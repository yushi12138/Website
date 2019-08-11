// alert('hello');
console.log('hello world');
//variables: var,let,const,
//dont use var, cause conflict
//let-->variable,const--->constant number
let age = 30;
age = 30;
console.log(age);

//data types
//primitive data: String, Numbers,Boolean,null,undefined,Symbol
const name = 'john';
const steps = 30;
const score = 4.5;
const isCool = true;
const x = null;
const y = undefined;
let z;
console.log(typeof steps);


//String
const s='aasd';
const ss =30;
//concatenation
console.log('my name is '+ s+' and my age is'+ss);
// template string using back-tick:``
const hello = `my name is ${s} and I am ${ss}`;
console.log(hello);
//method
console.log(s.length);
console.log(s.split(''));//split string into array by splitter


//Arrays- variables that hold multiple values
const numbers = new Array(1,2,3,4);
console.log(numbers);
const fruits = ['apples','peach',10,true];//like python,can put 
console.log(fruits);
console.log(fruits[0]);
fruits[1]='pineapple';
console.log(fruits[1]);
//method of array
fruits.push(0);
fruits.pop();
fruits.unshift('strawberry');


//object-orientation
const person={
    firstName:'John',
    lastName:'doe',
    age:30,
    hobbies:['',''],
    address:{
        street:'100 willoughby street',
        city:'brooklyn'
    }
}
console.log(person.age);
//get info from object
const { firstName, address:{ city } } = person;
console.log(firstName);
//add properties
person.email = 'john@gmail.com';
console.log(person.email);
//array of objects
const todos = [
    {
        id:'stone',
        isCool:true
    }
];

//json format, send data in json format,send to server
// [{"id":"stone"}]
const todoJSON = JSON.stringify(todos);
console.log(todoJSON);


// ---------------------------------------------------
//for loop
for(let i=0;i<10;i++){
    console.log(i);
}
//while loop
let i=0;
while(i++<10){
    console.log(i);
}
//for-each
for(let todo of todos){
    console.log(todo);
}

// funcitonal programming------------------------------
//forEach,map,filer
todos.forEach(function(todo){
    console.log(todo.id);
});
//map return sth
const todoID = todos.map(function(todo){
    return todo.id;
});
//filter out the object that satisfied condition
const todo_isCool = todos.filter(function(todo){
    return todo.isCool===true;
});
console.log(todo_isCool);


//conditional,
//==:'10'==10,===also check the data type
//always use === triple equal
const cond = '10';
if(cond===10){
    console.log("===");
}
if(cond==10){
    console.log('==');
}
//if-else if-else
if(true){
}else if(true){
}else{
}

//||,&&,
// ternary operater
const color = 10===10?'red':'blue';
console.log(color);
//switch
switch(color){
    case 'red':
        console.log('haha is red');
        break;
    case 'blue':
        console.log('haha blue');
        break;
    default:
        break;
}


// ---------------------function-------------------
function addNums(num1=1,num2=1){ //set default
    console.log(num1+num2);
    //return num1+num1;
}

addNums(1,2);
addNums();  //if not defaulted, return NaN(not a number)
//
const adds = (num1,num2)=> num1+num2;
console.log(adds(5,5));


//object-oriented
//constructor--can be used to directly create a class
function People(firstName,lastName,dob){
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = new Date(dob);
    this.getBirthYear = function(){
        return this.dob.getFullYear();
    }

    this.getFullName = function(){
        return `${this.firstName} ${this.lastName}`;
    }
}

//add method through prototype
People.prototype.getFirstName = function(){
    return this.firstName;
}

//instantiate object
const p1 = new People('john','stone','7-7-1994');
// console.log(p1.dob.getFullYear());
console.log(p1.getBirthYear());

//class
class Student{
    constructor(firstName,lastName){
        this.firstName= firstName;
        this.lastName = lastName;
    }

    getBirthYear(){

    }
    getFullName(){

    }
}

//Dom,document object model
console.log(window);// window is the parent of browser
alert(1);//don't need to include window to use alert();


//---------------single element-----------------
const form = document.getElementById('my-form');
console.log(form);
//query selector- like jquery(select other things than id)
//always select the first one
console.log(document.querySelector('h1'));

//---------------multiple element-----------------
//---always use document.querySelectorAll(),can do anything
console.log(document.querySelectorAll('.item'));
// console.log(document.getElementsByClassName('item'));//old stuff


//example--select list by class---------------------
const ul = document.querySelector('.items');
ul.remove();//remove the ul
ul.firstElementChild.textContent = 'stupid';
ul.children[1].innerHTML = '<h1>hello</h1>';
ul.lastElementChild.innerText = 'dfsd';
//change the style
const button = document.querySelector('.button');
botton.style.background = 'red';

//event:click,mouseover,mouseout,
buttone.addEventListener('click',(e)=>{
    e.preventDefault();//submit button
    document.querySelector('#my-form').style.background = '#ccc';
});


//EXAMPLE------submit list------------
const myForm= document.querySelector('#my-form');
const nameInput= document.querySelector('#name');
const emailInput= document.querySelector('#emaiil');
const msg= document.querySelector('.msg');
const userList= document.querySelector('#users');

myForm.addEventListener('submit',onSubmit);

function onSubmit(e){
    e.preventDefault();
    if(nameInput.value===''||emailInput.value===''){
        //add some style of class in .css to certain elelment
        msg.classList.add('error');//.error in css file
        setTimeout(()=>msg.remove(),3000);//3000ms,time counting

    }else{
        // console.log('success');
        const li = document.createElement('li');
        li.appendChild(document.createTextNode(
            `${nameInput.value} : ${emailInput.value}`));

            userList.appendChild(li);
            //clear
            nameInput.value='';
            emailInput.value='';
    }

}


