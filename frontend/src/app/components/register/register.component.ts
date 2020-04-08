import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerRequest;
  public error_messages;

  @Output()
  changeDisplay:EventEmitter<any> = new EventEmitter();

  constructor(private userService: UserService, private router:Router) {
    
    this.registerRequest = {};
    this.error_messages = {};
    this.error_messages.username = false;
    this.error_messages.username_taken = false;
    this.error_messages.password = false;
    this.error_messages.confirm_password = false;
    this.error_messages.name = false;
    this.error_messages.last_name = false;

   }

  ngOnInit(): void {
  }

  openRegistration(){
    this.changeDisplay.emit();
  }
  
  register(){

    if(!this.validForm())
      return;

    this.userService.sendRegisterRequest(this.registerRequest).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
  }

  validForm(): boolean{
    this.error_messages.username = false;
    this.error_messages.username_taken = false;
    this.error_messages.password = false;
    this.error_messages.confirm_password = false;
    this.error_messages.name = false;
    this.error_messages.last_name = false;

    var Successful = true;
    if(this.registerRequest.username != undefined){
      if(this.registerRequest.username.length < 3 || this.registerRequest.username.length >20){
        Successful = false;
        this.error_messages.username = true;
      }
    } else{
      Successful = false;
      this.error_messages.username = true;
    }

    if (this.registerRequest.password != undefined){
      if(this.registerRequest.password.length < 3 || this.registerRequest.password.length >20){
        Successful = false;
        this.error_messages.password = true;
      }
    } else{
      Successful = false;
      this.error_messages.password = true;
    }

    if (this.registerRequest.confirm_password != undefined){
      if(this.registerRequest.password != this.registerRequest.confirm_password){
        Successful = false;
        this.error_messages.confirm_password = true;
      }
    }else{
      Successful = false;
      this.error_messages.confirm_password = true;
    }

    if (this.registerRequest.name != undefined){
      if(this.registerRequest.name.length < 3 || this.registerRequest.name.length >20){
        Successful = false;
        this.error_messages.name = true;
      }
    } else{
      Successful = false;
      this.error_messages.name = true;
    }

    if (this.registerRequest.last_name != undefined){
      if(this.registerRequest.last_name.length < 3 || this.registerRequest.last_name.length >20){
        Successful = false;
        this.error_messages.last_name = true;
      }
    } else{
      Successful = false;
      this.error_messages.last_name = true;
    }



    return Successful;

  }

}
