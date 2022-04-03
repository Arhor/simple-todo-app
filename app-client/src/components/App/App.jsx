import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Container from '@mui/material/Container';
import PageHome from '@/components/PageHome';
import PageNotFound from '@/components/PageNotFound';

function App() {
    return (
        <BrowserRouter>
            <Container component="main">
                <Switch>
                    <Route component={PageHome} path="/" exact />
                    <Route component={PageNotFound} />
                </Switch>
            </Container>
        </BrowserRouter>
    );
}

export default App;
