const form = document.getElementById("rag-form");
const questionInput = document.getElementById("question");
const answer = document.getElementById("answer");
const statusPill = document.getElementById("status-pill");
const answerCard = document.querySelector(".answer-card");
const submitButton = form.querySelector("button[type='submit']");

function setStatus(label, state) {
    statusPill.textContent = label;
    statusPill.className = `status-pill ${state}`;
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const question = questionInput.value.trim();
    if (!question) {
        setStatus("Need a question", "error");
        answer.textContent = "Please enter a question about Pickering Is Springfield.";
        answer.classList.remove("placeholder");
        return;
    }

    setStatus("Thinking...", "loading");
    answerCard.setAttribute("aria-busy", "true");
    submitButton.disabled = true;
    answer.textContent = "Checking the book...";
    answer.classList.remove("placeholder");

    try {
        const response = await fetch(`/askrag?question=${encodeURIComponent(question)}`, {
            headers: {
                "Accept": "text/plain"
            }
        });

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        const text = await response.text();
        answer.textContent = text.trim() || "No answer was returned.";
        setStatus("Answered", "success");
    } catch (error) {
        answer.textContent = "Something went wrong while asking Springfield. Please try again.";
        setStatus("Try again", "error");
        console.error(error);
    } finally {
        answerCard.setAttribute("aria-busy", "false");
        submitButton.disabled = false;
    }
});
