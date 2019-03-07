package com.andres.metrics.collector.jobs;

import com.andres.metrics.collector.util.FileUtils;
import com.andres.metrics.collector.util.JSONConverter;
import com.andres.metrics.collector.metrics.LoggingMessages;
import com.andres.metrics.collector.metrics.MetricCollectorLogger;
import com.andres.metrics.collector.model.Parameter;
import net.minidev.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.andres.metrics.collector.model.DatabaseJobConfigTag.*;

public class DbQueryJob implements Job {

    private static final String SQL_SCRIPT_FOLDER = "/database/";

    @Autowired
    private Connection connection;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            PreparedStatement preparedStatement = generatePreparedStatement(jobExecutionContext);
            ResultSet resultSet = preparedStatement.executeQuery();
            JSONArray jsonArray = JSONConverter.convertToJsonArray(resultSet,
                    jobExecutionContext.getScheduler().getSchedulerName(),
                    jobExecutionContext.getJobDetail().getKey().getName());
            jsonArray.stream().forEach(element -> MetricCollectorLogger.logMessage(LoggingMessages.METRIC_00001, element));
        } catch (SQLException | SchedulerException e) {
            MetricCollectorLogger.logMessage(e, LoggingMessages.ERROR_00001);
        }
    }

    private PreparedStatement generatePreparedStatement(JobExecutionContext jobExecutionContext) throws SQLException {
        String statement = FileUtils.getContentAsSingleLine(SQL_SCRIPT_FOLDER + jobExecutionContext.getMergedJobDataMap().getString(STATEMENT_PARAMETER.tag()));
        int queryTimeOut = jobExecutionContext.getMergedJobDataMap().getInt(TIMEOUT_PARAMETER.tag());
        int queryMaxRows = jobExecutionContext.getMergedJobDataMap().getInt(MAX_ROWS_PARAMETER.tag());
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setQueryTimeout(queryTimeOut);
        preparedStatement.setMaxRows(queryMaxRows);
        addParametersToPreparedStatement(preparedStatement, getParameters(jobExecutionContext));
        return preparedStatement;
    }

    private List<Parameter> getParameters(JobExecutionContext jobExecutionContext) {
        JSONArray jsonArray = JSONConverter.convertToJsonArray(jobExecutionContext.getMergedJobDataMap().getString(PARAMETERS_PARAMETER.tag()));
        return JSONConverter.getParametersFromJsonArray(jsonArray);
    }

    private void addParametersToPreparedStatement(PreparedStatement preparedStatement, List<Parameter> parameters) throws SQLException {
        for (int index = 0; index < parameters.size(); index++) {
            preparedStatement.setObject(index + 1, parameters.get(index).getValue());
        }
    }
}
