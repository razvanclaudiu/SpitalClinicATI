package com.example.ati.repository;

import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;
import com.example.ati.domain.Type;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BedDBRepository implements Repository<Integer, Pat>{

    private final String url;
    private final String username;
    private final String password;

    public BedDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Pat getEntity(Integer integer) {
        return null;
    }

    @Override
    public Map<Integer, Pat> getAll() {
        Map<Integer,Pat> pats = new HashMap<>();
        String sql = "select beds.id, beds.tip, beds.ventilation, pacients.cnp, pacients.age, pacients.premature, pacients.diagnostic, pacients.gravity, pacients.in_bed from beds left join pacients on beds.pacient_id = pacients.cnp";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                Type tip = Type.valueOf(resultSet.getString("tip"));
                Boolean ventilator = resultSet.getBoolean("ventilation");
                long cnp = resultSet.getLong("cnp");
                int age = resultSet.getInt("age");
                boolean premature = resultSet.getBoolean("premature");
                String diagnosis = resultSet.getString("diagnostic");
                int gravity = resultSet.getInt("gravity");
                boolean  inBed = resultSet.getBoolean("in_bed");
                Pacient pacient = null;
                if (cnp != 0) {
                    pacient = new Pacient(age, premature, diagnosis, gravity, inBed);
                    pacient.setId(cnp);
                }
                Pat pat = new Pat(tip, ventilator, pacient);
                pat.setId(id);
                pats.put(id, pat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pats;
    }

    @Override
    public void add(Pat entity) throws RepositoryException {

    }

    @Override
    public void remove(Integer integer) throws RepositoryException {

    }

    @Override
    public void update(Pat entity) throws RepositoryException {
        String sql = "update beds set pacient_id = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            if(entity.getPacient() != null)
                ps.setLong(1, entity.getPacient().getId());
            else ps.setNull(1, Types.BIGINT);
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
