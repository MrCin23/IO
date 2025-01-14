package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.query.GeneralReportQuery;
//import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class GeneralReport extends Report {

    private String content;

    public GeneralReport(Long userId) {
        super(userId);
    }

    public String getContent() {
        return content;
    }

    @Override
    public GeneratedReport generate() {
        String name = null;
        String lastName = null;

        GeneralReportQuery grq = new GeneralReportQuery();
        Set<String> result = grq.nameSurnameQuery(getUserId());
        Iterator<String> iterator = result.iterator();
        if (!result.isEmpty()) {
            name = iterator.next();
            lastName = iterator.next();
        }

        int usersCount = grq.countUsers();
        content = "Raport ogolny" + "\n" + "Ilosc uzytkownikow: " + usersCount;

        return new GeneratedReport(getUserId(), name, lastName, content);
    }
}
