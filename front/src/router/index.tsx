import {Route, Routes, Navigate} from 'react-router-dom'
import {
    defaultRoutes,
    aid_organizationRoutes,
    authority_representativeRoutes, donorRoutes, victimRoutes, volunteerRoutes, reportRoutes, chatRoutes
} from './routes.ts'
import {DefaultLayout} from "../components/layouts/default";
import {AidOrganizationLayout} from "../components/layouts/aid_organization";
import {AuthorityRepresentativeLayout} from "../components/layouts/autority_representative";
import {DonorLayout} from "../components/layouts/donor/DonorLayout.tsx";
import {VictimLayout} from "../components/layouts/victim";
import {VolunteerLayout} from "../components/layouts/volunteer";
import {ReportLayout} from "../components/layouts/report";
import { useAccount } from '../contexts/uwierzytelnianie/AccountContext.tsx';
import PrivateRoute from '../components/PrivateRoute.tsx';
import { Pathnames } from './pathnames.ts';

/** Komponent rutera definiuje możliwe ścieżki (konteksty URL), które prowadzą do określonych widoków (komponentów)
 * Używana jest do tego mapa łącząca ścieżkę z komponentem.
 * Tu użyta jest konstrukcja używająca wielu map, w zamyśle dzieli ona widoki ze względu na dostępność dla poszczególnych poziomów dostępu
 * Dla uproszczenia we wszystkich przypadkach jest używany ten sam szablon strony, ale można by stworzyć wiele szablonów i zmieniać wygląd aplikacji
 *
 * @see routes
 * @see DefaultLayout
 */
export const RoutesComponent = () => {
    const { account } = useAccount();

    type Role = 'ORGANIZACJA_POMOCOWA' | 'PRZEDSTAWICIEL_WŁADZ' | 'DARCZYŃCA' | 'POSZKODOWANY' | 'WOLONTARIUSZ';

    const roleHomePageMapping : Record<Role, string> = {
        'ORGANIZACJA_POMOCOWA': Pathnames.aid_organization.homePage,
        'PRZEDSTAWICIEL_WŁADZ': Pathnames.authority_representative.homePage,
        'DARCZYŃCA': Pathnames.donor.homePage,
        'POSZKODOWANY': Pathnames.victim.homePage,
        'WOLONTARIUSZ': Pathnames.volunteer.homePage,
    };

    const renderRoutes = () => {
        if (!account) {
            return defaultRoutes.map(({ path, Component }) => (
                <Route key={path} path={path} element={
                    <DefaultLayout>
                        <Component />
                    </DefaultLayout>
                }
                />
            ));
        }

        switch (account.role.roleName) {
            case 'ORGANIZACJA_POMOCOWA':
                return aid_organizationRoutes.map(({ path, Component }) => (
                    <Route
                        key={path}
                        path={path}
                        element={
                            <PrivateRoute allowedRoles={['ORGANIZACJA_POMOCOWA']}>
                                <AidOrganizationLayout>
                                    <Component />
                                </AidOrganizationLayout>
                            </PrivateRoute>
                        }
                    />
                ));
            case 'PRZEDSTAWICIEL_WŁADZ':
                return authority_representativeRoutes.map(({ path, Component }) => (
                    <Route
                        key={path}
                        path={path}
                        element={
                            <PrivateRoute allowedRoles={['PRZEDSTAWICIEL_WŁADZ']}>
                                <AuthorityRepresentativeLayout>
                                    <Component />
                                </AuthorityRepresentativeLayout>
                            </PrivateRoute>
                        }
                    />
                ));
            case 'DARCZYŃCA':
                return donorRoutes.map(({ path, Component }) => (
                    <Route
                        key={path}
                        path={path}
                        element={
                            <PrivateRoute allowedRoles={['DARCZYŃCA']}>
                                <DonorLayout>
                                    <Component />
                                </DonorLayout>
                            </PrivateRoute>
                        }
                    />
                ));
            case 'POSZKODOWANY':
                return victimRoutes.map(({ path, Component }) => (
                    <Route
                        key={path}
                        path={path}
                        element={
                            <PrivateRoute allowedRoles={['POSZKODOWANY']}>
                                <VictimLayout>
                                    <Component />
                                </VictimLayout>
                            </PrivateRoute>
                        }
                    />
                ));
            case 'WOLONTARIUSZ':
                return volunteerRoutes.map(({ path, Component }) => (
                    <Route
                        key={path}
                        path={path}
                        element={
                            <PrivateRoute allowedRoles={['WOLONTARIUSZ']}>
                                <VolunteerLayout>
                                    <Component />
                                </VolunteerLayout>
                            </PrivateRoute>
                        }
                    />
                ));
            default:
                return null;
        }
    };

    return (
        <Routes>
            {renderRoutes()}
            <Route
                path="*"
                element={
                    account ? (
                        <Navigate to={roleHomePageMapping[account.role.roleName as Role] || Pathnames.default.homePage} />
                    ) : (
                        <Navigate to={Pathnames.default.homePage} />
                    )
                }
                />

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
            {chatRoutes.map(({path, Component}) => (
                <Route key={path} path={path} element={
                    <Component/>
                } />
            ))}
        </Routes>
    );
}