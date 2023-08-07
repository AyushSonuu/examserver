import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { PasswordChecker } from './custom-validators/password-checker';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'online-exam-system';
  registerForm!: FormGroup;
  submitted = false;

  constructor(private formBuilder:FormBuilder){}
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      email: ["", Validators.required],
      userName:["",Validators.required],
      phone:["",Validators.required],
      password: ["", Validators.required],
      confirmPassword: ["", Validators.required],
    }, {
      validators: PasswordChecker('password', 'confirmPassword')
    })
  }



}
