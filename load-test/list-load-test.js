import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 50,
    duration: '30s'
};

const BASE = 'http://localhost:8080';
const LIST = `${BASE}/api/articles?page=0&size=10`;

export default function () {
    const params = {
        headers: {
        },
    };

    const res = http.get(LIST, params);

    check(res, {
        'list 200': (r) => r.status === 200,
    });

    sleep(0.1); // 100 ms think-time
}
