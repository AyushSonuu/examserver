import { FormGroup } from "@angular/forms";

export function PasswordChecker(controlName: string,
    CompareControName: string){

        return (formGroup:FormGroup)=>{
            const password = formGroup.controls[controlName];
            const confPassword = formGroup.controls[CompareControName];
            if (password.value !== confPassword.value) {
                confPassword.setErrors({ mustMatch: true });
            } else {
                confPassword.setErrors(null);
            }
        }

}