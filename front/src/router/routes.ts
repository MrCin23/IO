import { Pathnames } from './pathnames'


import {Pom} from "../pages/mapExamples/Pom.tsx";
import {ExternalFormPage} from "../pages/mapExamples/ExternalFormPage.tsx";

import {HomePage} from "../pages/HomePage";
import LoginPage from '../pages/uwierzytelnianie/LoginPage';
import RegisterPage from '../pages/uwierzytelnianie/RegisterPage';
import MyAccountPage from '../pages/uwierzytelnianie/MyAccountPage';
import AccountsListPage from '../pages/uwierzytelnianie/AccountsListPage';
import VictimPage from '../pages/VictimPage';

/** Tutaj dodajemy komponenty które będą zawierać strony.
 * Jeśli coś występuje w więce niż jednym widoku należy dodać to do każdego w którym występuje, z odpowiednim pathname
 */
export type RouteType = {
    Component: () => React.ReactElement,
    path: string
}

export const defaultRoutes: RouteType[] = [
    {
        path: Pathnames.default.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.default.loginPage,
        Component: LoginPage,
    },
    {
        path: Pathnames.default.registerPage,
        Component: RegisterPage,
    },

    {
        path: Pathnames.default.pomPage,
        Component: Pom,
    }, //todo do wywalenia
    {
        path: Pathnames.default.externalForm,
        Component: ExternalFormPage,
    } //todo do wywalenia
]

export const aid_organizationRoutes: RouteType[] = [
    {
        path: Pathnames.aid_organization.homePage,
        Component: HomePage,
    }

]

export const authority_representativeRoutes: RouteType[] = [
    {
        path: Pathnames.authority_representative.homePage,
        Component: HomePage,
    }
]

export const donorRoutes: RouteType[] = [
    {
        path: Pathnames.donor.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.donor.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.donor.accountsListPage,
        Component: AccountsListPage,
    }
]

export const victimRoutes: RouteType[] = [
    {
        path: Pathnames.victim.homePage,
        Component: VictimPage,
    }
]

export const volunteerRoutes: RouteType[] = [
    {
        path: Pathnames.volunteer.homePage,
        Component: HomePage,
    }
]
