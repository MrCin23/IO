/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    // TODO tutaj należy dodawać ścieżki do poszczególnych stron
    // TODO WAŻNE info: niech ścieżki przestrzegają przedrostków jak niżej
    default: {
        homePage: '/',
        loginPage: '/login',
        registerPage: '/register',
        pomPage: '/pom', //todo do wywalenia
        externalForm: '/form'//todo do wywalenia
    },
    aid_organization: {
        homePage: '/organization',
    },
    authority_representative: {
        homePage: '/authority'
    },
    donor: {
        homePage: '/donor',
        accountPage: '/donor/account',
        accountsListPage: '/donor/accounts/list',
    },
    victim: {
        homePage: '/victim'
    },
    volunteer: {
        homePage: '/volunteer'
    },
    report: {
        homePage: '/report'
    }
}