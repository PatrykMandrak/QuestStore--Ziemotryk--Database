package databaseAccess;

import models.Backpack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BackpacksDAO implements IDAO<Backpack> {

    private DbConnector dbConnector = DbConnector.getInstance();

    @Override
    public void add(Backpack toAdd) {
        dbConnector.executeUpdate(
                "INSERT INTO backpacks(backpack_id, artifact_id, is_used)\n"
                        + "VALUES (" + toAdd.getBackpackId() + ","
                        + "" + toAdd.getArtifactId() + ","
                        + "'" + Boolean.toString(toAdd.isUsed()).toUpperCase() + ");"
        );
    }

    @Override
    public Backpack get(int id) {
        ResultSet resultSet = dbConnector.getResultSetByQuery(
                "SELECT * FROM backpacks WHERE backpack_id="+id
        );
        Backpack backpack = null;

        try {
            resultSet.next();
            backpack = new Backpack(
                    resultSet.getInt("backpack_id"),
                    resultSet.getInt("artifact_id"),
                    resultSet.getBoolean("is_used")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return backpack;
    }

    @Override
    public List<Backpack> getAll() {
        List<Backpack> backpacks = new ArrayList<>();
        ResultSet resultSet = dbConnector.getResultSetByQuery(
                "SELECT * FROM backpacks"
        );

        try {
            while(resultSet.next()) {
                backpacks.add(
                        new Backpack(
                                resultSet.getInt("backpack_id"),
                                resultSet.getInt("artifact_id"),
                                resultSet.getBoolean("is_used")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return backpacks;
    }

    @Override
    public void update(int id, Backpack toUpdate) {
        dbConnector.executeUpdate(
                "UPDATE backpacks\n"
                      + "SET backpack_id=" + toUpdate.getBackpackId() + ",\n"
                      + "artifact_id=" + toUpdate.getArtifactId() + ",\n"
                      + "is_used=" + Boolean.toString(toUpdate.isUsed()).toUpperCase() + "\n"
                      + "WHERE backpack_id=" + id
        );
    }

    @Override
    public void delete(int id) {
        dbConnector.executeUpdate(
                "DELETE FROM backpacks WHERE backpack_id=" + id
        );
    }
}
