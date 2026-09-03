const STORAGE_KEY = 'simpsons-trivia-history';
const form = document.getElementById('trivia-form');
const promptInput = document.getElementById('prompt');
const submitBtn = document.getElementById('submit-btn');
const sampleBtn = document.getElementById('sample-btn');
const responseContent = document.getElementById('response-content');
const loading = document.getElementById('loading');
const historyList = document.getElementById('history-list');
const clearHistoryBtn = document.getElementById('clear-history');

let history = loadHistory();

const sampleQuestions = [
  'Who is the owner of the Kwik-E-Mart?',
  'What is the name of the Simpsons family dog?',
  'Which character is known for saying "D\'oh!"?',
  'What is the name of Springfield\'s nuclear power plant?',
  'Which Simpsons character is obsessed with donuts?'
];

function loadHistory() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : [];
  } catch (error) {
    console.error('Could not parse localStorage history', error);
    return [];
  }
}

function saveHistory() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(history));
}

function renderHistory() {
  if (!history.length) {
    historyList.innerHTML = '<li class="empty-state">No Simpsons questions yet. Ask one and it will appear here.</li>';
    return;
  }

  historyList.innerHTML = history
    .map(
      (entry, index) => `
        <li>
          <button class="history-item" type="button" data-index="${index}">
            <strong>${escapeHtml(entry.question)}</strong>
            <span class="history-meta">${formatDate(entry.timestamp)}</span>
          </button>
        </li>
      `
    )
    .join('');

  historyList.querySelectorAll('.history-item').forEach((button) => {
    button.addEventListener('click', () => {
      const index = Number(button.dataset.index);
      showHistoryEntry(history[index]);
    });
  });
}

function showHistoryEntry(entry) {
  responseContent.textContent = entry.answer;
  responseContent.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function formatDate(timestamp) {
  return new Date(timestamp).toLocaleString([], {
    month: 'short',
    day: 'numeric',
    hour: 'numeric',
    minute: '2-digit'
  });
}

function escapeHtml(value) {
  return value
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

function triggerCelebration() {
  const layer = document.createElement('div');
  layer.className = 'confetti-layer';
  const emojis = ['🎉', '💥', '🍩', '⭐', '✨', '🥤'];

  for (let i = 0; i < 30; i++) {
    const piece = document.createElement('span');
    piece.className = 'confetti';
    piece.textContent = emojis[Math.floor(Math.random() * emojis.length)];
    piece.style.left = `${Math.random() * 100}%`;
    piece.style.setProperty('--x', `${(Math.random() - 0.5) * 600}px`);
    piece.style.setProperty('--r', `${(Math.random() - 0.5) * 720}deg`);
    piece.style.animationDelay = `${Math.random() * 0.25}s`;
    layer.appendChild(piece);
  }

  document.body.appendChild(layer);
  setTimeout(() => layer.remove(), 1800);
}

async function handleSubmit(event) {
  event.preventDefault();

  const question = promptInput.value.trim();
  if (!question) {
    promptInput.focus();
    return;
  }

  submitBtn.disabled = true;
  loading.classList.remove('hidden');

  try {
    const response = await fetch(`/trivia?prompt=${encodeURIComponent(question)}`);
    if (!response.ok) {
      throw new Error(`Request failed with status ${response.status}`);
    }

    const answer = (await response.text()).trim();
    const entry = {
      question,
      answer: answer || 'The Simpsons expert is taking a coffee break. Try again!',
      timestamp: new Date().toISOString()
    };

    history = [entry, ...history].slice(0, 12);
    saveHistory();
    renderHistory();
    showHistoryEntry(entry);
    triggerCelebration();
  } catch (error) {
    console.error(error);
    const fallback = 'The Springfield AI is taking a donut break. Please try again in a moment.';
    const entry = { question, answer: fallback, timestamp: new Date().toISOString() };

    history = [entry, ...history].slice(0, 12);
    saveHistory();
    renderHistory();
    showHistoryEntry(entry);
  } finally {
    loading.classList.add('hidden');
    submitBtn.disabled = false;
    form.reset();
    promptInput.focus();
  }
}

sampleBtn.addEventListener('click', () => {
  const randomQuestion = sampleQuestions[Math.floor(Math.random() * sampleQuestions.length)];
  promptInput.value = randomQuestion;
  promptInput.focus();
});

clearHistoryBtn.addEventListener('click', () => {
  history = [];
  saveHistory();
  renderHistory();
  responseContent.textContent = 'Ask a question and the Springfield brain will answer in style.';
});

form.addEventListener('submit', handleSubmit);
renderHistory();
