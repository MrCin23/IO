import { Pathnames } from './pathnames'

import {HomePage} from "../pages/HomePage";
import {Resources} from "../pages/Resources";
import {Warehouses} from "../pages/Warehouses";
import {CreateWarehouse} from "../pages/CreateWarehouse";
import {CreateResource} from "../pages/CreateResource";

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
]
