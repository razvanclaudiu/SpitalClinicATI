package com.example.ati.repository;

import com.example.ati.domain.Pacient;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PacientDBRepository implements Repository<Long, Pacient>{

    private final String url;
    private final String username;
    private final String password;

    public PacientDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Pacient getEntity(Long aDouble) {
        return null;
    }

    @Override
    public Map<Long, Pacient> getAll() {
        Map<Long,Pacient> pacients = new HashMap<>();
        String sql = "select * from pacients";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long cnp = resultSet.getLong("cnp");
                int age = resultSet.getInt("age");
                boolean premature = resultSet.getBoolean("premature");
                String diagnosis = resultSet.getString("diagnostic");
                int gravity = resultSet.getInt("gravity");
                boolean inBed = resultSet.getBoolean("in_bed");
                Pacient pacient = new Pacient(age, premature, diagnosis, gravity,inBed);
                pacient.setId(cnp);
                pacients.put(cnp, pacient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacients;
    }

    @Override
    public void add(Pacient entity) throws RepositoryException {

    }

    @Override
    public void remove(Long aDouble) throws RepositoryException {

    }

    @Override
    public void update(Pacient entity) throws RepositoryException {
        String sql = "update pacients set age = ?, premature = ?, diagnostic = ?, gravity = ?, in_bed = ? where cnp = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, entity.getAge());
            ps.setBoolean(2, entity.getPremature());
            ps.setString(3, entity.getDiagnosis());
            ps.setInt(4, entity.getGravity());
            ps.setBoolean(5, entity.getInBed());
            ps.setLong(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
