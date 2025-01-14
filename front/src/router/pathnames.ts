/** Definiuje kolekcję ścieżek (kontekstów URL), które mogą prowadzić do widoków aplikacji
 */
export const Pathnames = {
    // TODO tutaj należy dodawać ścieżki do poszczególnych stron
    // TODO WAŻNE info: niech ścieżki przestrzegają przedrostków jak niżej
    default: {
        homePage: '/',
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
        homePage: '/donor'
    },
    victim: {
        homePage: '/victim'
    },
    volunteer: {
        homePage: '/volunteer'
    }
}