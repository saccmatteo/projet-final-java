package dataAccess;

import java.sql.*;
import java.time.LocalDate;

public class crudUtils {

    // Méthodes pour set des nulls potentiels

    public static void setNullableInt(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.INTEGER);
        }
    }

    public static void setNullableDate(PreparedStatement ps, int parameterIndex, LocalDate value) throws SQLException {
        if (value != null) {
            ps.setDate(parameterIndex, Date.valueOf(value));
        } else {
            ps.setNull(parameterIndex, Types.DATE);
        }
    }

    public static void setNullableDouble(PreparedStatement ps, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            ps.setDouble(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.DOUBLE);
        }
    }

    public static void setNullableString(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
        if (value != null) {
            ps.setString(parameterIndex, value);
        } else {
            ps.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    // Méthodes pour get des null potentiels

    public static Integer getNullableInt(ResultSet rs, String columnLabel) throws SQLException {
        int value = rs.getInt(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static Double getNullableDouble(ResultSet rs, String columnLabel) throws SQLException {
        double value = rs.getDouble(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static String getNullableString(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static LocalDate getNullableDate(ResultSet rs, String columnLabel) throws SQLException {
        java.sql.Date date = rs.getDate(columnLabel);
        return rs.wasNull() ? null : date.toLocalDate();
    }
}
