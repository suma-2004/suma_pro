const chatMessages = document.getElementById('chat-messages');
const userInput = document.getElementById('user-input');
const sendButton = document.getElementById('send-button');

function displayMessage(text, sender) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', sender);
    messageDiv.textContent = text;
    chatMessages.appendChild(messageDiv);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

function sendMessage() {
    const userQuestion = userInput.value.trim();
    if (userQuestion === '') return;

    displayMessage(userQuestion, 'user');
    userInput.value = '';

    // Replace these predefined responses with your FAQ data
    const faqResponses = {
        'What is your product?': 'Our product is a chatbot platform.',
        'How can I contact support?': 'You can contact our support team at support@example.com.',
        'Where is your company located?': 'Our company is located in City, Country.',
        'hello': 'Hey! How is it going?',
        'what is your name': 'You can call me MyAI! I m here to chat and have fun with you.What s up?',
        'what is the capital of ap': 'The capital of Andhra Pradesh is Amaravati. Its a beautiful city! Have you ever been  there?',
        'Do u have girl friend': 'No, I dont have a girlfriend. Just here to be your virtual friend and chat with you!',
        'what is your fav song': 'I enjoy a lot of different songs, but one of my favorites is "Shape of You" by Ed Sheeran. What about you? What s your favorite song?',
    };

    const botResponse = faqResponses[userQuestion] || 'I do not have an answer for that question.';
    displayMessage(botResponse, 'bot');
}

sendButton.addEventListener('click', sendMessage);
userInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        sendMessage();
    }
});