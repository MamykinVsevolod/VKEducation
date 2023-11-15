from django.shortcuts import render
from django.http import HttpResponse


# Create your views here.
def index(request):
    questions = [
        {
            'id': i,
            'title': f'Вопрос {i}',
            'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
        } for i in range(10)
    ]
    return render(request, 'index.html', {'questions': questions})


def question(request):
    return render(request, 'question.html')


def ask(request):
    return render(request, 'ask.html')


def login(request):
    return render(request, 'login.html')


def settings(request):
    return render(request, 'settings.html')


def signup(request):
    return render(request, 'signup.html')
