/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    // TODO tutaj należy dodawać ścieżki do poszczególnych stron
    // TODO WAŻNE info: niech ścieżki przestrzegają przedrostków jak niżej
    default: {
        homePage: '/',
        loginPage: '/login',
        registerPage: '/register'
    },
    aid_organization: {
        homePage: '/organization',
        accountPage: '/organization/account',
        resources: '/organization/resources',
        warehouses: '/organization/warehouses',
        createWarehouse: '/organization/warehouses/create',
        createResource: '/organization/resources/create',
    },
    authority_representative: {
        homePage: '/authority',
        accountPage: '/authority/account',
        accountsListPage: '/authority/accounts/list',
        resources: '/authority/resources',
        warehouses: '/authority/warehouses',
        createWarehouse: '/authority/warehouses/create',
        createResource: '/authority/resources/create',
    },
    donor: {
        homePage: '/donor',
        financialDonations: '/financial-donations',
        itemDonations: '/item-donations',
        accountPage: '/donor/account',
        accountsListPage: '/donor/accounts/list',
        createResource: '/donor/resources/create'
    },
    victim: {
        homePage: '/victim',
        accountPage: '/victim/account',
        resources: '/victim/resources'
    },
    volunteer: {
        homePage: '/volunteer',
        volunteers: '/volunteers',
        groups: '/volunteer/groups',
        accountPage: '/volunteer/account',
        resources: '/volunteer/resources'
    },
    chat: {
        homePage: '/chat'

    }
}