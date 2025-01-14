/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    // TODO tutaj należy dodawać ścieżki do poszczególnych stron
    // TODO WAŻNE info: niech ścieżki przestrzegają przedrostków jak niżej
    default: {
        homePage: '/',
    },
    aid_organization: {
        homePage: '/organization',
        resources: '/organization/resources',
        warehouses: '/organization/warehouses',
        createWarehouse: '/organization/warehouses/create',
        createResource: '/organization/resources/create',
    },
    authority_representative: {
        homePage: '/authority',
        resources: '/authority/resources',
        warehouses: '/authority/warehouses',
        createWarehouse: '/authority/warehouses/create',
        createResource: '/authority/resources/create',
    },
    donor: {
        homePage: '/donor',
        createResource: '/donor/resources/create'
    },
    victim: {
        homePage: '/victim',
        resources: '/victim/resources'
    },
    volunteer: {
        homePage: '/volunteer'
    }
}