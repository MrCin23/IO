import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Poszkodowani from './poszkodowani/Poszkodowani'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Poszkodowani />
  </StrictMode>,
)
