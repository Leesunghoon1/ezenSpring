/**
 * 
 */

async function removeFileFromServer(fx) {
    try{
        const url='/board/file/' + fx;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch(err) {
        console.log(err);
    }
}


document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.classList.contains('file-x')) {
        //삭제작업
        let fx = e.target.dataset.uuit;
        console.log(fx);

        //삭제구현
        let div = e.target.closest('div');
        removeFileFromServer(fx).then(result=>{
            if(result == 1){
               alert('댓글 삭제 성공~!!');
               e.target.closest('li').remove(); // li 삭제
               location.reload();
            }else{
               alert('댓글 삭제 실패~!!');
            }
         });
    }
})