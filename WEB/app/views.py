from django.shortcuts import render

# Create your views here.
QUESTIONS = [
    {
        'id': i,
        'title': f'Вопрос {i}',
        'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
    } for i in range(10)
]


def index(request):
    return render(request, 'index.html', {'questions': QUESTIONS})


def question(request, question_id):
    item = QUESTIONS[question_id]
    return render(request, 'question.html', {'question': item})


def ask(request):
    return render(request, 'ask.html')


def login(request):
    return render(request, 'login.html')


def settings(request):
    return render(request, 'settings.html')


def signup(request):
    return render(request, 'signup.html')
