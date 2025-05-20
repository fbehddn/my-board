import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 50,
    duration: '1m',
    rps: 200,
};

const BASE = 'http://localhost:8080';
const SIGNUP = `${BASE}/api/users/signup`;
const LOGIN  = `${BASE}/api/auth/login`;
const CREATE = `${BASE}/api/articles`;         // 게시글 생성 API

export function setup() {
    // ─ 1) 테스트용 관리 계정 하나 만들고 로그인
    const admin = { username: 'admin', email: 'admin@tb.com', password: 'Admin123!' };
    let res = http.post(SIGNUP, JSON.stringify(admin), { headers:{ 'Content-Type':'application/json' } });
    // 이미 있으면 409 OK
    check(res, { 'admin signup ok': r => r.status === 201 || r.status === 409 });

    res = http.post(LOGIN, JSON.stringify({ email: admin.email, password: admin.password }),
        { headers:{ 'Content-Type':'application/json' } });
    check(res, { 'admin login ok': r => r.status === 200 });
    const token = res.json('accessToken');

    const auth = { headers:{
            'Content-Type':'application/json',
            'Authorization': `Bearer ${token}`
        }};

    // ─ 2) 게시글 100개 일괄 생성
    const ids = [];
    for (let i = 1; i <= 100; i++) {
        const art = {
            title: `Seed Article #${i}`,
            content: `This is seed content for article ${i}`
        };
        res = http.post(CREATE, JSON.stringify(art), auth);
        check(res, { 'create article': r => r.status === 201 });
        ids.push(res.json('id'));  // 생성된 PK ID
    }

    return { articleIds: ids,token };
}

export default function(data) {
    const ids = data.articleIds;
    const articleId = ids[Math.floor(Math.random() * ids.length)];

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${data.token}`
    };
    const res = http.post(`${BASE}/api/likes/articles/${articleId}`, null, { headers });

    check(res, { 'like 200': r => r.status === 200 });
    sleep(0.1);
}
