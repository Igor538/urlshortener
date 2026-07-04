let userId = localStorage.getItem("userId");

if (!userId) {
    userId = crypto.randomUUID();
    localStorage.setItem("userId", userId);
}

async function encurtar(url) {
    const res = await fetch("https://urlshortener-7g5s.onrender.com/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-USER-ID": userId
        },
        body: JSON.stringify({ url })
    });

    return await res.json();
}

async function listarLinks() {
    const res = await fetch("https://urlshortener-7g5s.onrender.com/links", {
        headers: {
            "X-USER-ID": userId
        }
    });

    return await res.json();
}