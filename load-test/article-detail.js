// load-test/load-test.js
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 50,          // 동시 사용자 수
    rps: 200,         // 초당 최대 요청 수
    duration: '15s',   // 테스트 전체 시간
};

const BASE   = 'http://localhost:8080';
const SIGNUP = `${BASE}/api/users/signup`;
const LOGIN  = `${BASE}/api/auth/login`;
const CREATE = `${BASE}/api/articles`;
const DETAIL = (id) => `${BASE}/api/articles/${id}`;

export function setup() {
    // ─────────────────────────────────────────────
    // 1) 회원가입 → 로그인 → 토큰 확보
    // ─────────────────────────────────────────────
    const user = {
        username: `lt_usr_${Date.now()}`,
        email:    `lt_usr_${Date.now()}@test.com`,
        password: 'TestPass123!',
    };

    let res = http.post(SIGNUP, JSON.stringify(user), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(res, { 'signup 201|409': r => r.status === 201 || r.status === 409 });

    res = http.post(LOGIN,
        JSON.stringify({ email: user.email, password: user.password }),
        { headers: { 'Content-Type': 'application/json' } }
    );
    check(res, { 'login 200': r => r.status === 200 });
    const token = res.json('accessToken');

    const authHeaders = {
        headers: {
            'Content-Type':  'application/json',
            'Authorization': `Bearer ${token}`,
        },
    };

    // ─────────────────────────────────────────────
    // 2) 더미 게시글 100개 생성
    // ─────────────────────────────────────────────
    const ids = [];
    for (let i = 1; i <= 100; i++) {
        const body = { title: `Seed #${i}`, content: `Content ${i}` };
        res = http.post(CREATE, JSON.stringify(body), authHeaders);
        check(res, { 'create 201': r => r.status === 201 });
        ids.push(res.json('id'));
    }

    return { token, articleIds: ids };
}

export default function(data) {
    // ─────────────────────────────────────────────
    // 3) 랜덤 ID로 상세 조회 (캐시 히트/미스 분리 없이 동일 호출)
    // ─────────────────────────────────────────────
    const id = data.articleIds[
        Math.floor(Math.random() * data.articleIds.length)
        ];

    const res = http.get(DETAIL(id), {
        headers: { Authorization: `Bearer ${data.token}` },
    });
    check(res, { 'detail 200': r => r.status === 200 });

    sleep(0.05);
}