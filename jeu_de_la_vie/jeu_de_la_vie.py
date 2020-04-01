import tkinter as tk
from random import *
from tkinter import ALL
import time
import threading
from math import *
R = 0
C = 0
h = 650
w = 645
cell_size = 20
FPS = 3000
M=[]
aret = False
init_live = 0

def draw_cell_by_cr(canvas,c,r,color="white"):
    global cell_size
    x0 = c*cell_size
    y0 = r*cell_size
    x1 = c*cell_size + cell_size
    y1 = r*cell_size + cell_size
    canvas.create_rectangle(x0,y0,x1,y1,fill = color,outline = "black")

def draw_blank_board(canvas):
    global R
    global C
    global cell_size
    
    R = int(650/cell_size)
    C = int(645/cell_size)
    for i in range(R):
        for j in range(C):
            draw_cell_by_cr(canvas,j,i)
    
def set_canvas(cell_size):
    global canvas
    global R
    global C

    canvas = tk.Canvas(root,width = 645, height = 650)
    canvas.place(relx = 0,rely = 0)
    draw_blank_board(canvas)

def size(v): 
    global canvas
    global R
    global C
    canvas.delete("all") 
    
    global cell_size
    cell_size = 20 + s.get()*0.3

    set_canvas(cell_size)

def initialiser():
    global R
    global C
    global M
    M = []
    global canvas
    global init_live
    draw_blank_board(canvas)
    for i in range(R):
        M.append([])
        for j in range(C):
            #rand = randrange(2)
            rand = random()
            if rand <= init_live:                    
                draw_cell_by_cr(canvas,j,i,"red")
                rand = 1
                #init_live = init_live - 1
            rand = floor(rand)
            M[i].append(rand)
    
def nb_voisin(i,j):
    global R
    global C    
    nb_voisin=0
    global M
    for k in range(i-1,i+2):            
        if M[k%R][(j-1)%C]!=0:
            nb_voisin+=1
        if M[k%R][(j+1)%C]!=0:   
            nb_voisin+=1
        if k%R!=i and M[k%R][j]!=0:
            nb_voisin+=1
    return nb_voisin

def jeu_de_vie():
    global R      
    global C
    global M
    voisin=0
    vecteur=[]
    global aret
    global FPS
    global canvas
    if aret == False:   
        for i in range(R):
            vecteur.append([])
            for j in range (C):
                voisin = nb_voisin(i,j)
                if voisin==2:
                    vecteur[i].append(M[i][j])  
                    continue
                if voisin==3:
                    vecteur[i].append(1)
                    draw_cell_by_cr(canvas,j,i,"red")
                    continue
                else:
                    vecteur[i].append(0)
                    draw_cell_by_cr(canvas,j,i,"white")           
        M = vecteur.copy()
        root.update()
        root.after(FPS,jeu_de_vie)

#pour le botton lancer
def start():
    global aret
    aret = False

    thread = threading.Thread(target=jeu_de_vie)
    thread.start()

#pour le botton arreter
def stop():
    global aret
    aret = True

#changer le temp de flash
def get_fps(v):
    global FPS
    FPS = 3000-25*s2.get()

def count(v):
    global init_live
    init_live = float(s1.get()/100)
 
root = tk.Tk()
root.title('Jeu de la vie')
root.geometry('800x645')
tk.Label(root,width = 21,height = 800, bg='#CCCCCC').pack(side='right')
set_canvas(cell_size)

b1 = tk.Button(root,text = 'Lancer',  width = 20, height = 1,command = start)
b1.place(x=650,y=0)
b2 = tk.Button(root,text = 'Arreter', width = 20, height = 1,command = stop)
b2.place(x=650,y=30)
b3 = tk.Button(root,text = 'Initialiser', width = 20, height = 1,command = initialiser)
b3.place(x=650,y=60)
b4 = tk.Button(root,text = 'Quitter', width = 20, height = 1,command = root.quit)
b4.place(x=650,y=615)

s = tk.Scale(root,label='Taille de grille',from_=0,to=100,orient=tk.HORIZONTAL,length = 100,
    resolution = 1)
s.bind("<ButtonRelease-1>",size)
s.place(x=670,y=395)
s1 = tk.Scale(root,label='%'+' de vie',from_=0,to=100,orient=tk.HORIZONTAL,length = 100,
    resolution = 1,command = count)
s1.place(x=670,y=465)
s2 = tk.Scale(root,label='vitesse',from_=0,to=100,orient=tk.HORIZONTAL,length = 100,
    resolution = 1,command = get_fps)
s2.place(x=670,y=545)

root.mainloop()