import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import {HomeReportPage} from "../pages/report/HomeReportPage";
import LoginPage from '../pages/uwierzytelnianie/LoginPage';
import RegisterPage from '../pages/uwierzytelnianie/RegisterPage';
import MyAccountPage from '../pages/uwierzytelnianie/MyAccountPage';
import AccountsListPage from '../pages/uwierzytelnianie/AccountsListPage';

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
    }
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
        Component: HomePage,
    }
]

export const volunteerRoutes: RouteType[] = [
    {
        path: Pathnames.volunteer.homePage,
        Component: HomePage,
    }
]

export const reportRoutes: RouteType[] = [
    {
        path: Pathnames.report.homePage,
        Component: HomeReportPage,
    }
]
