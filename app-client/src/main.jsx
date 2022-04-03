import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@mui/material/CssBaseline';
import { ThemeProvider } from '@mui/material/styles';

import App from '@/components/App';
import ErrorBoundary from '@/components/ErrorBoundary';
import theme from '@/theme.js';

ReactDOM.render(
    <React.StrictMode>
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <ErrorBoundary>
                <App />
            </ErrorBoundary>
        </ThemeProvider>
    </React.StrictMode>,
    document.getElementById('root'),
);
