import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';
import dotenv from 'dotenv';

dotenv.config({ path: '../.env' });

export default defineConfig({
    plugins: [react()],

    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
    },

    server: {
        proxy: {
            '^/api': {
                target: process.env.API_BASE_URL,
                changeOrigin: true,
            },
        },
    },
});
