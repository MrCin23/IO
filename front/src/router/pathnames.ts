/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    // TODO tutaj należy dodawać ścieżki do poszczególnych stron
    // TODO WAŻNE info: niech ścieżki przestrzegają przedrostków jak niżej
    default: {
        homePage: '/',
        loginPage: '/login',
        registerPage: '/register',
    },
    aid_organization: {
        homePage: '/organization',
        accountPage: '/organization/account',
    },
    authority_representative: {
        homePage: '/authority',
        accountPage: '/authority/account',
        accountsListPage: '/authority/accounts/list',
    },
    donor: {
        homePage: '/donor',
        accountPage: '/donor/account',
    },
    victim: {
        homePage: '/victim',
        accountPage: '/victim/account',
    },
    volunteer: {
        homePage: '/volunteer',
        accountPage: '/volunteer/account',
    },
    chat: {
        homePage: '/chat'
        
    }
}