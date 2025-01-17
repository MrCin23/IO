import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import LoginPage from '../pages/uwierzytelnianie/LoginPage';
import RegisterPage from '../pages/uwierzytelnianie/RegisterPage';
import MyAccountPage from '../pages/uwierzytelnianie/MyAccountPage';
import AccountsListPage from '../pages/uwierzytelnianie/AccountsListPage';
import FinancialDonationList from "@/components/layouts/donor/components/FinancialDonationList.tsx";
import ItemDonationList from "@/components/layouts/donor/components/ItemDonationList.tsx";
import DonationPanel from "@/components/layouts/donor/components/DonationPanel.tsx";
import VolunteerGroupList from "@/components/layouts/volunteer/components/VolunteerGroupList.tsx";
import VolunteerList from "@/components/layouts/volunteer/components/VolunteerList.tsx";
import volunteerInfoPage from "@/components/layouts/volunteer/components/volunteerInfoPage.tsx";

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
    },
    {
        path: Pathnames.aid_organization.accountPage,
        Component: MyAccountPage,
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
    },
    {
        path: Pathnames.victim.accountPage,
        Component: MyAccountPage,
    }
]

export const volunteerRoutes: RouteType[] = [
    {
        path: Pathnames.volunteer.homePage,
        Component: HomePage,
    },
    {
        path: Pathnames.volunteer.groups,
        Component: VolunteerGroupList,
    },
    {
        path: Pathnames.volunteer.volunteers,
        Component: VolunteerList,
    },
    {
        path: Pathnames.volunteer.accountPage,
        Component: volunteerInfoPage,
    }
]
