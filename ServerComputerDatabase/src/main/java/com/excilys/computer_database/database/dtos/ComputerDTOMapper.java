package com.excilys.computer_database.database.dtos;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.mappers.Mapper;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helpers.DateHelper;

public class ComputerDTOMapper extends DTO implements Mapper<ComputerDTO, Computer> {
    @Override
    public ComputerDTO unmap(Computer c) {
        return new ComputerDTO(c);
    }

    public static ComputerDTO unmap(ResultSet rs) throws DAOException {
        try {
            return new ComputerDTO(rs.getLong(1), rs.getString(2), DateHelper.timestampToLocalDate(rs.getTimestamp(3)),
                    DateHelper.timestampToLocalDate(rs.getTimestamp(4)), rs.getLong(5), rs.getString(6));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
