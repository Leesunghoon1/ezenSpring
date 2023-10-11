console.log(bnoVal);
console.log(id);
async function postCommentToServer(cmtData)
{
    try {
        const url="/comment/post";
        const config={
            method:"post",
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtData)
        };

        const resp=await fetch(url,config);
        const result=await resp.text(); //isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

    document.getElementById('cmtPostBtn').addEventListener('click',()=>{
    	console.log('11');
        const cmtText=document.getElementById('cmtText').value;
        const cmtWriter=document.getElementById('cmtWriter').innerText;
        if(cmtText==""||cmtText==null){
            alert("댓글을 입력해주세요.");
            document.getElementById('cmtText').focus();
            return false;
        }
        else
        {
            let cmtData={
                bno:bnoVal,
                writer:cmtWriter,
                content:cmtText
            }
            console.log(cmtData);
            postCommentToServer(cmtData).then(result=>{
                console.log(result);
                //isOk 확인
                if(result==1)
                {
                    alert('댓글등록 성공~!!');
                    //화면에 뿌리기
                    getCommentList(bnoVal);
                }
            })
        }
    })
//서버에 댓글 리스트를 달라고 요청    //서버에 댓글 리스트를 달라고 요청 
    async function spreadCommentListFromServer(bno) {
        try{
            const resp = await fetch('/comment/'+bno);
            const result = await resp.json();
            return result;
        }catch(err) {
            console.log(err);
        }
    }

    function getCommentList(bno){
        spreadCommentListFromServer(bno).then(result =>{
            console.log(result);
            //화면에 뿌리기
        //     <div>
        //    <!-- 댓글 표시 라인 -->
        //         <ul id="cmtListArea">
        //             <li>
        //             <div>
        //                 <div>writer</div>
        //                 content
        //             </div>
        //             <span>reg_date</span>
        //           </li>
        //      </ul>
        //   </div>
        // </div>
            
            let div = document.getElementById('cmtListArea');
            if(result.length > 0) {
                div.innerHTML="";
                
                for(let i =0; i< result.length; i++) {
                    
                    let str = `<li data-cno="${result[i].cno}"><div>`
                    str += `<div class="cmtListArea">`
                    str += `${result[i].writer}`
                    str += `</div>`
                    str += `<input type="text" id="cmtTextMod" value="${result[i].content}">`
                    str += `</div>`
                    str += `<span>`
                    str += `${result[i].regdate}`
                    str += `</span>`
                    if(result[i].writer === id) {
                        str += `<button type="button" class="modBtn">%</button>`
                        str += `<button type="button" class="delBtn">x</button>`
                    }
                    str += `</li>`
                    str += `</div>`
                    div.innerHTML+= str;

            }
        }
            else{
                let li = `<li>CommentListEmpty</li>`
                div.innerHTML = li;
            }
        })
    }


    async function editCommentToServer(cmtModData) {
        try{    
            const url = '/comment/'+cmtModData.cno;
            const config = {
                method:'put',
                headers:{
                    'content-type':'application/json; charset=utf-8'
                },
                body:JSON.stringify(cmtModData)
            };
            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        }catch(err) {
            console.lof(err);
        }
    }

    async function removeCommentToServer(cno) {
        try{
            const url='/comment/' + cno;
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

    document.addEventListener('click', (e)=>{
        console.log(e.target);
        if(e.target.classList.contains('modBtn')) {
            //수정작업
            console.log('수정버튼 클릭~!!');
            //내가 선택 한 타겟과 가장 가까운 li를 찾기
            let li = e.target.closest('li')
            let conVal = li.dataset.cno;
            let textContent = li.querySelector('#cmtTextMod').value;
       
            console.log("cno / content == " + conVal + " / " + textContent)

            let cmtModData = {
                cno : conVal,
                content : textContent
            };
            console.log(cmtModData);
            //서버연결
            editCommentToServer(cmtModData).then(result =>{
                if(result == 1) {
                    alert("댓글 수정 성공~!!");
                }
                getCommentList(bnoVal);
            })

        }else if(e.target.classList.contains('delBtn')) {
            //삭제작업
            console.log('삭제버튼 클랙~!!');
            let li = e.target.closest('li')
            let conVal = li.dataset.cno;
            //서버연결
            removeCommentToServer(conVal).then(result =>{
                if(result == 1) {
                    alert("댓글 삭제 성공~!!");
                }
                getCommentList(bnoVal);
            })


        }

    })
