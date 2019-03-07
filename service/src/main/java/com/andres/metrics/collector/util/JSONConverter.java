package com.andres.metrics.collector.util;

import com.andres.metrics.collector.metrics.LoggingMessages;
import com.andres.metrics.collector.metrics.MetricCollectorLogger;
import com.andres.metrics.collector.model.Parameter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JSONConverter {

    private static final String SERVICE_NAME_TAG = "collector_service";
    private static final String METRIC_NAME_TAG = "collector_metric";
    private static final String INDEX_TAG = "query_index";

    public static JSONArray convertToJsonArray(ResultSet resultSet, String serviceName, String metricName) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        int rowCount = 0;
        while (resultSet.next()) {
            int numberOfColumns = resultSet.getMetaData().getColumnCount();
            JSONObject jsonColumn = new JSONObject();
            for (int i = 0; i < numberOfColumns; i++) {
                jsonColumn.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
            }
            jsonColumn.put(SERVICE_NAME_TAG, serviceName);
            jsonColumn.put(METRIC_NAME_TAG, metricName);
            jsonColumn.put(INDEX_TAG, rowCount++);
            jsonArray.add(jsonColumn);
        }
        return jsonArray;
    }

    public static JSONArray convertToJsonArray(List<Parameter> parameters) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        if (parameters != null) {
            for (Parameter parameter : parameters) {
                jsonObject = new JSONObject();
                jsonObject.put(Parameter.NAME_TAG, parameter.getName());
                jsonObject.put(Parameter.VALUE_TAG, parameter.getValue());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public static JSONArray convertToJsonArray(String jsonString) {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONParser p = new JSONParser(JSONParser.MODE_PERMISSIVE);
            jsonArray = (JSONArray) p.parse(jsonString);
        } catch (Exception e) {
            MetricCollectorLogger.logMessage(e, LoggingMessages.ERROR_00001);
        }
        return jsonArray;
    }

    public static List<Parameter> getParametersFromJsonArray(JSONArray jsonArray) {
        List<Parameter> parameters = new ArrayList<>();
        jsonArray.stream().forEach(jsonObject -> {
            Parameter parameter = new Parameter();
            parameter.setName((String) ((JSONObject) jsonObject).get(Parameter.NAME_TAG));
            parameter.setValue(((JSONObject) jsonObject).get(Parameter.VALUE_TAG));
            parameters.add(parameter);
        });
        return parameters;
    }
}