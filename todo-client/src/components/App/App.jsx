import React, { useState } from 'react';
import logo from '@/assets/img/logo.svg';
import '@/components/App/App.css';

const SimpleLink = ({ to, text }) => (
    <a className="App-link" href={to} target="_blank" rel="noopener noreferrer">
        {text}
    </a>
);

function App() {
    const [count, setCount] = useState(0);

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>Hello Vite + React!</p>
                <p>
                    <button type="button" onClick={() => setCount((count) => count + 1)}>
                        count is: {count}
                    </button>
                </p>
                <p>
                    Edit <code>App.jsx</code> and save to test HMR updates.
                </p>
                <p>
                    <SimpleLink to="https://reactjs.org" text="Learn React" />
                    {' | '}
                    <SimpleLink to="https://vitejs.dev/guide/features.html" text="Vite Docs" />
                </p>
            </header>
        </div>
    );
}

export default App;
