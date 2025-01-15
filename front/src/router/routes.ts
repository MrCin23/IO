import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import {Resources} from "../pages/Resources";
import {Warehouses} from "../pages/Warehouses";
import {CreateWarehouse} from "../pages/CreateWarehouse";
import {CreateResource} from "../pages/CreateResource";
import LoginPage from '../pages/uwierzytelnianie/LoginPage';
import RegisterPage from '../pages/uwierzytelnianie/RegisterPage';
import MyAccountPage from '../pages/uwierzytelnianie/MyAccountPage';
import AccountsListPage from '../pages/uwierzytelnianie/AccountsListPage';
import FinancialDonationList from "@/components/layouts/donor/components/FinancialDonationList.tsx";
import ItemDonationList from "@/components/layouts/donor/components/ItemDonationList.tsx";
import DonationPanel from "@/components/layouts/donor/components/DonationPanel.tsx";

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
        Component: DonationPanel,
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
        path: Pathnames.donor.financialDonations,
        Component: FinancialDonationList
    },
    {
        path: Pathnames.donor.itemDonations,
        Component: ItemDonationList
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

export const resourceRoutes: RouteType[] = [
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
    },
    {
        path: Pathnames.victim.resources,
        Component: Resources,
    },
    {
        path: Pathnames.donor.createResource,
        Component: CreateResource,
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
    },
]
