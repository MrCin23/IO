package pl.lodz.p.ias.io.raportowanie.model.type;

import lombok.Getter;

public class ModularReport extends Report {
    @Getter
    private int moduleId;

    private enum Module {
        RESOURCES(1),
        WAREHOUSES(2),
        PRODUCTS(3),
        ROLES(4),
        VOLUNTEER_GROUPS(5);

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

    public ModularReport(Long userId, int moduleId) {
        super(userId);
        this.moduleId = moduleId;
    }
}
