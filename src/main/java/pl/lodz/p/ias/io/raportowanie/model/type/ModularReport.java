package pl.lodz.p.ias.io.raportowanie.model.type;

import pl.lodz.p.ias.io.raportowanie.model.entity.GeneratedReport;
import pl.lodz.p.ias.io.raportowanie.query.GeneralReportQuery;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

public class ModularReport extends GeneralReport {

    private int moduleId;
    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private Set<String> fields;

    private enum Module {
        MODULE1(1),
        MODULE2(2),
        MODULE3(3),
        MODULE4(4),
        MODULE5(5),
        MODULE6(6),
        MODULE7(7),
        MODULE8(8),
        MODULE9(9);

        private final int id;

        Module(int id) {
            this.id = id;
        }

        public static Module getById(int id) {
            for (Module module : values()) {
                if (module.getId() == id) {
                    return module;
                }
            }
            throw new IllegalArgumentException("No module with id " + id);
        }

        public int getId() {
            return id;
        }
    }

    public ModularReport(Long userId, int moduleId, Timestamp startTime, Timestamp endTime, Set<String> fields) {
        super(userId);
        this.moduleId = moduleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fields = fields;
    }

    public GeneratedReport generate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GeneralReportQuery grq = new GeneralReportQuery();

        String startDate = dateFormat.format(startTime);
        String endDate = dateFormat.format(endTime);

        Module module = Module.getById(moduleId);
        /*switch (moduleId) {
            case 1:
                content = "Raport modularny dla modulu " + module + "\n"
                        + "Wygenerowany dla danych z przedzialu:\n"
                        + startDate + " - " + endDate + "\n"
                        + "Ilosc zgloszen potrzeb: 10";

                return new GeneratedReport(getUserId(), content);
            case 2:
                content = "Raport modularny dla modulu " + module + "\n"
                        + "Wygenerowany dla danych z przedzialu:\n"
                        + startDate + " - " + endDate + "\n"
                        + "Ilosc zasobow: 10";

                return new GeneratedReport(getUserId(), content);
            case 4:
                content = "Raport modularny dla modulu " + module + "\n"
                        + "Wygenerowany dla danych z przedzialu:\n"
                        + startDate + " - " + endDate + "\n"
                        + "Ilosc darowizn: 10";

                return new GeneratedReport(getUserId(), content);
            case 5:
                content = "Raport modularny dla modulu " + module + "\n"
                        + "Wygenerowany dla danych z przedzialu:\n"
                        + startDate + " - " + endDate + "\n"
                        + "Ilosc wolontariuszy: 10";

                return new GeneratedReport(getUserId(), content);
            case 8:
                content = "Raport modularny dla modulu " + module + "\n"
                        + "Wygenerowany dla danych z przedzialu:\n"
                        + startDate + " - " + endDate + "\n"
                        + "Ilosc uzytkownikow: 10";

                return new GeneratedReport(getUserId(), content);
            default:
                throw new IllegalArgumentException("No module with id " + moduleId);
        }*/
        return null;
    }
}
