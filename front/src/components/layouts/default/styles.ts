import styled from 'styled-components';

export const LayoutWrapper = styled.div`
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    margin: 0 auto; /* Środek strony */
    max-width: 1280px; /* Maksymalna szerokość strony */
    padding: 2rem; /* Margines wewnętrzny */
    background-color: #f5f5f5; /* Neutralne tło */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Subtelny cień */
    border-radius: 8px; /* Zaokrąglone rogi */
`;

export const ContentWrapper = styled.div`
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 16px;
    background-color: white;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin: 16px;
    border-radius: 8px;
    overflow: auto; /* Zapobiega nadmiarowemu przewijaniu */
`;