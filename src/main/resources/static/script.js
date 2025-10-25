const apiBaseUrl = ""; // same origin by default
let authToken = null;

const registerForm = document.getElementById("register-form");
const loginForm = document.getElementById("login-form");
const fetchAccountsForm = document.getElementById("fetch-accounts-form");
const createAccountForm = document.getElementById("create-account-form");
const transferForm = document.getElementById("transfer-form");
const tokenDisplay = document.getElementById("token-display");
const tokenValue = document.getElementById("token-value");
const logoutBtn = document.getElementById("logout-btn");
const accountsList = document.getElementById("accounts-list");

function setStatus(elementId, message, type = "") {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = message;
    el.classList.remove("success", "error");
    if (type) {
        el.classList.add(type);
    }
}

function showToken(token) {
    if (!token) {
        tokenDisplay.hidden = true;
        tokenValue.textContent = "";
        return;
    }
    tokenDisplay.hidden = false;
    tokenValue.textContent = token;
}

function rememberToken(token) {
    authToken = token;
    if (token) {
        localStorage.setItem("bankingToken", token);
    } else {
        localStorage.removeItem("bankingToken");
    }
    showToken(token);
}

async function authorizedFetch(url, options = {}) {
    if (!authToken) {
        throw new Error("Please login to call this endpoint.");
    }
    const headers = new Headers(options.headers || {});
    headers.set("Authorization", `Bearer ${authToken}`);
    return fetch(`${apiBaseUrl}${url}`, { ...options, headers });
}

function formDataToJson(form) {
    return Object.fromEntries(new FormData(form).entries());
}

function renderAccounts(accounts) {
    if (!accounts || accounts.length === 0) {
        accountsList.classList.add("empty");
        accountsList.innerHTML = "<p>No accounts found for this user.</p>";
        return;
    }

    accountsList.classList.remove("empty");
    accountsList.innerHTML = "";

    accounts.forEach((account) => {
        const card = document.createElement("div");
        card.className = "account-card";
        card.innerHTML = `
            <div><strong>ID:</strong> ${account.id}</div>
            <div><strong>Account #:</strong> ${account.accountNumber}</div>
            <div><strong>Currency:</strong> ${account.currency}</div>
            <div><strong>Balance:</strong> ${account.balance}</div>
        `;
        accountsList.appendChild(card);
    });
}

registerForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("register-status", "Registering…");
    try {
        const body = formDataToJson(registerForm);
        const response = await fetch(`${apiBaseUrl}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "Registration failed");
        }

        const data = await response.json();
        rememberToken(data.token);
        setStatus("register-status", "Registration successful! Token stored.", "success");
    } catch (error) {
        console.error(error);
        setStatus("register-status", error.message, "error");
    }
});

loginForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("login-status", "Logging in…");
    try {
        const body = formDataToJson(loginForm);
        const response = await fetch(`${apiBaseUrl}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "Login failed");
        }

        const data = await response.json();
        rememberToken(data.token);
        setStatus("login-status", "Logged in successfully!", "success");
    } catch (error) {
        console.error(error);
        setStatus("login-status", error.message, "error");
    }
});

fetchAccountsForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("fetch-accounts-status", "Loading accounts…");
    try {
        const { userId } = formDataToJson(fetchAccountsForm);
        const response = await authorizedFetch(`/api/accounts/user/${userId}`);

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "Unable to load accounts");
        }

        const data = await response.json();
        renderAccounts(data);
        setStatus("fetch-accounts-status", `Loaded ${data.length} account(s).`, "success");
    } catch (error) {
        console.error(error);
        setStatus("fetch-accounts-status", error.message, "error");
    }
});

createAccountForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("create-account-status", "Creating account…");
    try {
        const { userId, currency, initialBalance } = formDataToJson(createAccountForm);
        const params = new URLSearchParams({ userId, currency, initialBalance });
        const response = await authorizedFetch(`/api/accounts?${params.toString()}`, {
            method: "POST",
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "Account creation failed");
        }

        const account = await response.json();
        setStatus(
            "create-account-status",
            `Account ${account.accountNumber} created with balance ${account.balance}.`,
            "success"
        );
    } catch (error) {
        console.error(error);
        setStatus("create-account-status", error.message, "error");
    }
});

transferForm?.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("transfer-status", "Processing transfer…");
    try {
        const body = formDataToJson(transferForm);
        const params = new URLSearchParams(body);
        const response = await authorizedFetch(`/api/transactions/transfer?${params.toString()}`, {
            method: "POST",
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "Transfer failed");
        }

        const transaction = await response.json();
        setStatus(
            "transfer-status",
            `Transfer completed! ID: ${transaction.id}, amount: ${transaction.amount}.`,
            "success"
        );
    } catch (error) {
        console.error(error);
        setStatus("transfer-status", error.message, "error");
    }
});

logoutBtn?.addEventListener("click", () => {
    rememberToken(null);
    setStatus("login-status", "Logged out.", "success");
});

(function restoreToken() {
    const savedToken = localStorage.getItem("bankingToken");
    if (savedToken) {
        authToken = savedToken;
        showToken(savedToken);
    }
})();
