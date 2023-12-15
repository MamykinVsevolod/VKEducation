from django.shortcuts import render
from django.core.paginator import Paginator

# Create your views here.
QUESTIONS = [
    {
        'id': i,
        'title': f'Вопрос {i}',
        'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
    } for i in range(50)
]

ANSWERS = [
    {
        'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
    } for i in range(50)
]

HOT_QUESTIONS = [
    {
        'id': i,
        'title': f'Вопрос {i*i}',
        'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
    } for i in range(20)
]

TAG_QUESTIONS = [
    {
        'id': i,
        'title': f'Вопрос {i}',
        'content': 'VK — это более 200 проектов и сервисов, которыми пользуются миллионы. Ты можешь присоединиться к команде, которая их создаёт. Познакомься с нашим офисом и посети виртуальную экскурсию.'
    } for i in range(20)
]

def paginate(objects, page, per_page=5):
    paginator = Paginator(objects, per_page)
    obj = paginator.get_page(page)
    # page_items = paginator.page(1).object_list
    """ return paginator.page(page) """
    return obj


def get_paginator(objects, per_page=5):
    paginator = Paginator(objects, per_page)
    return paginator


def index(request, page=1):
    # paginator = Paginator(QUESTIONS, 3)
    # page_items = paginator.page(1).object_list
    """return render(request, 'index.html', {'questions': paginate(QUESTIONS, page), page: page})"""
    return render(request, 'index.html', {'questions': paginate(QUESTIONS, page), 'paginator': get_paginator(QUESTIONS)})


def hot(request, page=1):
    return render(request, 'hot.html',
                  {'questions': paginate(HOT_QUESTIONS, page), 'paginator': get_paginator(HOT_QUESTIONS)})


def tag(request, tag_name, page=1):
    # paginator = Paginator(QUESTIONS, 3)
    # page_items = paginator.page(1).object_list
    """return render(request, 'index.html', {'questions': paginate(QUESTIONS, page), page: page})"""
    return render(request, 'tag.html', {'tag': tag_name, 'questions': paginate(TAG_QUESTIONS, page), 'paginator': get_paginator(TAG_QUESTIONS)})


def question(request, question_id, page=1):
    item = QUESTIONS[question_id]
    return render(request, 'question.html', {'question': item, 'answers': paginate(ANSWERS, page), 'paginator': get_paginator(ANSWERS)})


def ask(request):
    return render(request, 'ask.html')


def login(request):
    return render(request, 'login.html')


def settings(request):
    return render(request, 'settings.html')


def signup(request):
    return render(request, 'signup.html')
