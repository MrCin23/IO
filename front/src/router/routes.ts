import { Pathnames } from './pathnames'

import Resources from "../pages/Resources";
import Warehouses from "../pages/Warehouses";
import {CreateWarehouse} from "../pages/CreateWarehouse";
import {CreateResource} from "../pages/CreateResource";
import Chat from '@/pages/Chat';
import {HomePage} from "../pages/HomePage";
import LoginPage from '../pages/uwierzytelnianie/LoginPage';
import RegisterPage from '../pages/uwierzytelnianie/RegisterPage';
import MyAccountPage from '../pages/uwierzytelnianie/MyAccountPage';
import AccountsListPage from '../pages/uwierzytelnianie/AccountsListPage';
// import VictimPage from '../pages/VictimPage';

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
        path: Pathnames.default.loginPage,
        Component: LoginPage,
    }
]

export const aid_organizationRoutes: RouteType[] = [
    {
        path: Pathnames.aid_organization.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.aid_organization.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.aid_organization.resources,
        Component: Resources,
    },
    {
        path: Pathnames.aid_organization.warehouses,
        Component: Warehouses,
    },
    {
        path: Pathnames.aid_organization.createWarehouse,
        Component: CreateWarehouse,
    },
    {
        path: Pathnames.aid_organization.createResource,
        Component: CreateResource,
    }
]

export const authority_representativeRoutes: RouteType[] = [
    {
        path: Pathnames.authority_representative.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.authority_representative.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.authority_representative.accountsListPage,
        Component: AccountsListPage,
    },
    {
        path: Pathnames.authority_representative.resources,
        Component: Resources,
    },
    {
        path: Pathnames.authority_representative.warehouses,
        Component: Warehouses,
    },
    {
        path: Pathnames.authority_representative.createWarehouse,
        Component: CreateWarehouse,
    },
    {
        path: Pathnames.authority_representative.createResource,
        Component: CreateResource,
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
    },
    {
        path: Pathnames.donor.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.donor.createResource,
        Component: CreateResource,
    }
]

export const victimRoutes: RouteType[] = [
    {
        path: Pathnames.victim.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.victim.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.victim.resources,
        Component: Resources,
        //Component: VictimPage,
    }
]

export const volunteerRoutes: RouteType[] = [
    {
        path: Pathnames.volunteer.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.volunteer.accountPage,
        Component: MyAccountPage,
    },
    {
        path: Pathnames.volunteer.resources,
        Component: Resources,
    }
]

export const chatRoutes: RouteType[] = [
    {
        path: Pathnames.chat.homePage,
        Component: Chat,
    }
]
