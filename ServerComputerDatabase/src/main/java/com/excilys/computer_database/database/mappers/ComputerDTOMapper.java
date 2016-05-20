package com.excilys.computer_database.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.LocalDate;

import com.excilys.computer_database.database.dao.DAOException;
import com.excilys.computer_database.database.dtos.ComputerDTO;
import com.excilys.computer_database.database.dtos.DTO;
import com.excilys.computer_database.entity.Computer;
import com.excilys.computer_database.helpers.DateHelper;

public class ComputerDTOMapper extends DTO implements Mapper<ComputerDTO, Computer> {
    @Override
    public ComputerDTO unmap(Computer c) {
        return new ComputerDTO(c);
    }

    public static ComputerDTO unmap(ResultSet rs) throws DAOException {
        try {
            Timestamp introTimestamp = rs.getTimestamp(3);
            LocalDate introduced = introTimestamp == null ? null : DateHelper.timestampToLocalDate(introTimestamp);
            Timestamp discTimestamp = rs.getTimestamp(4);
            LocalDate discontinued = discTimestamp == null ? null : DateHelper.timestampToLocalDate(discTimestamp);

            return new ComputerDTO(rs.getLong(1), rs.getString(2), introduced,
                    discontinued, rs.getLong(5), rs.getString(6));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
