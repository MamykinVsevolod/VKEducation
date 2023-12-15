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


def paginate(objects, page, per_page=5):
    paginator = Paginator(objects, per_page)
    obj = paginator.get_page(page)
    # page_items = paginator.page(1).object_list
    """ return paginator.page(page) """
    return obj


def all_pages(objects, page, per_page=5):
    paginator = Paginator(objects, per_page)
    return paginator


def index(request, page=1):
    # paginator = Paginator(QUESTIONS, 3)
    # page_items = paginator.page(1).object_list
    """return render(request, 'index.html', {'questions': paginate(QUESTIONS, page), page: page})"""
    return render(request, 'index.html', {'questions': paginate(QUESTIONS, page), 'paginator': all_pages(QUESTIONS, page)})


# return render(request, 'index.html', {'questions': QUESTIONS})


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
