import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import {AuthenticationService, RegisterRequest, User} from "../data-access/api";
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  hide = true;

  dropdownList=[];

  form = this.formBuilder.group({
    firstname:['', [Validators.required]],
    lastname: ['', Validators.required],
    username: ['', Validators.required],
    age: ['' , Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    hobbies: [[]],
    preferences: [[]],
  });

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {

  }

  onSubmit() {
    let registerRequest: RegisterRequest = {
      firstname: this.form.controls['firstname'].value ? this.form.controls['firstname'].value : undefined,
      lastname:  this.form.controls['lastname'].value ? this.form.controls['lastname'].value : undefined,
      username: this.form.controls['username'].value ? this.form.controls['username'].value : undefined,
      email: this.form.controls['email'].value ? this.form.controls['email'].value : undefined,
      password: this.form.controls['password'].value ? this.form.controls['password'].value : undefined
    };
    this.authService.register(registerRequest).subscribe((response) => {
      this.router.navigateByUrl('/login');
    });


  }

  check() {
    if (this.form.get('password')) {
      return !this.form.get('password')!.toString().match('^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$')
    }
    return false;
  }

}
