"""
URL configuration for HomeWork2 project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls')
"""
from django.contrib import admin
from django.urls import path
from app import views

urlpatterns = [
    path('', views.index, name='start'),
    path('<int:page>', views.index, name='index'),
    path('hot/<int:page>', views.hot, name='hot'),
    path('tag/<str:tag_name>/<int:page>', views.tag, name='tag'),
    path('question/<int:question_id>/<int:page>', views.question, name='question'),
    path('ask', views.ask, name='ask'),
    path('login', views.login, name='login'),
    path('settings', views.settings, name='settings'),
    path('signup', views.signup, name='signup'),
    path('admin/', admin.site.urls)
]
