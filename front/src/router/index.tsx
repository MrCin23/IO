import {Route, Routes} from 'react-router-dom'
import {
    defaultRoutes,
    aid_organizationRoutes,
    authority_representativeRoutes, donorRoutes, victimRoutes, volunteerRoutes, reportRoutes
} from './routes.ts'
import {DefaultLayout} from "../components/layouts/default";
import {AidOrganizationLayout} from "../components/layouts/aid_organization";
import {AuthorityRepresentativeLayout} from "../components/layouts/autority_representative";
import {DonorLayout} from "../components/layouts/donor";
import {VictimLayout} from "../components/layouts/victim";
import {VolunteerLayout} from "../components/layouts/volunteer";
import {ReportLayout} from "../components/layouts/report";

/** Komponent rutera definiuje możliwe ścieżki (konteksty URL), które prowadzą do określonych widoków (komponentów)
 * Używana jest do tego mapa łącząca ścieżkę z komponentem.
 * Tu użyta jest konstrukcja używająca wielu map, w zamyśle dzieli ona widoki ze względu na dostępność dla poszczególnych poziomów dostępu
 * Dla uproszczenia we wszystkich przypadkach jest używany ten sam szablon strony, ale można by stworzyć wiele szablonów i zmieniać wygląd aplikacji
 *
 * @see routes
 * @see DefaultLayout
 */
export const RoutesComponent = () => {


    return (
        <Routes>
            {defaultRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <DefaultLayout>
                        <Component />
                    </DefaultLayout>
                }
                />
            ))}
            {aid_organizationRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <AidOrganizationLayout>
                        <Component />
                    </AidOrganizationLayout>
                }
                />
            ))}
            {authority_representativeRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <AuthorityRepresentativeLayout>
                        <Component />
                    </AuthorityRepresentativeLayout>
                }
                />
            ))}
            {donorRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <DonorLayout>
                        <Component />
                    </DonorLayout>
                }
                />
            ))}
            {victimRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <VictimLayout>
                        <Component />
                    </VictimLayout>
                }
                />
            ))}
            {volunteerRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <VolunteerLayout>
                        <Component />
                    </VolunteerLayout>
                }
                />
            ))}
            {reportRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <ReportLayout>
                        <Component />
                    </ReportLayout>
                }
                />
            ))}
        </Routes>
    )
}