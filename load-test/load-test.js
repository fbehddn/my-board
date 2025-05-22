import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    scenarios: {
        baseline: {
            executor: 'constant-arrival-rate',
            rate: 20,
            timeUnit: '1s',
            duration: '10m',
            preAllocatedVUs: 30,
            maxVUs: 100,
        },
        spike: {
            executor: 'constant-arrival-rate',
            rate: 100,
            timeUnit: '1s',
            duration: '2m',
            startTime: '10m',      // baseline 종료 시점
            preAllocatedVUs: 100,
            maxVUs: 200,
        },
    },
};

const BASE   = 'http://localhost:8080';
const SIGNUP = `${BASE}/api/users/signup`;
const LOGIN  = `${BASE}/api/auth/login`;
const CREATE = `${BASE}/api/articles`;
const LIKE   = (id) => `${BASE}/api/likes/articles/${id}`;

export function setup() {
    // 1) Admin 한 번만 signup & login
    const admin = { username: 'admin', email: 'admin@tb.com', password: 'Admin123!' };
    let res = http.post(SIGNUP, JSON.stringify(admin), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(res, { 'admin signup ok': (r) => r.status === 201 || r.status === 409 });

    res = http.post(
        LOGIN,
        JSON.stringify({ email: admin.email, password: admin.password }),
        { headers: { 'Content-Type': 'application/json' } }
    );
    check(res, { 'admin login ok': (r) => r.status === 200 });
    const token = res.json('accessToken');

    const authHeaders = {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
    };

    // 2) Seed용 게시글 100개 생성
    const ids = [];
    for (let i = 1; i <= 100; i++) {
        const art = { title: `Seed Article #${i}`, content: `Seed content for article ${i}` };
        res = http.post(CREATE, JSON.stringify(art), { headers: authHeaders });
        check(res, { 'create article ok': (r) => r.status === 201 });
        ids.push(res.json('id'));
    }

    return { articleIds: ids, token };
}

export default function (data) {
    // 오직 좋아요 요청만
    const ids       = data.articleIds;
    const articleId = ids[Math.floor(Math.random() * ids.length)];
    const headers   = {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${data.token}`,
    };

    const res = http.post(LIKE(articleId), null, { headers });
    check(res, { 'like 200': (r) => r.status === 200 });
    sleep(0.1);
}