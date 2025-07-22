// Troca tela LOGIN -> CADASTRO
document.getElementById('botao-mostrar-cadastro').addEventListener('click', () => {
    document.getElementById('secao-login').style.display = 'none';
    document.getElementById('secao-cadastro').style.display = 'block';
    document.getElementById('formulario-login').reset();
});

// Troca tela CADASTRO -> LOGIN
document.getElementById('botao-mostrar-login').addEventListener('click', () => {
    document.getElementById('secao-cadastro').style.display = 'none';
    document.getElementById('secao-login').style.display = 'block';
    document.getElementById('formulario-cadastro').reset();
});

// LOGIN
document.getElementById('formulario-login').addEventListener('submit', (evento) => {
    evento.preventDefault();

    const email = document.getElementById('usuario-login').value.trim();
    const senha = document.getElementById('senha-login').value.trim();
    const Auth = 'Basic '+ btoa(email + ':' + senha);
    localStorage.setItem('Auth', Auth); 

    if (email == '' || senha == '') {
        alert('Preencha usuário e senha');
        return;
    }

    fetch('http://localhost:8080/usuarios/me', {
        method: 'GET',
        headers: { 'Authorization' : Auth }
    })
    .then(resposta => {if (!resposta.ok) 
        {
            throw new Error('Usuário não encontrado: ' + resposta.status)
        }
    return resposta.json();})
    .then(user => 
        {
            if (user.role == 'CLIENTE')
            {
                document.getElementById('secao-login').style.display = 'none';
                document.getElementById('secao-cadastro').style.display = 'none';
                document.getElementById('secao-cliente').style.display = 'block';
                document.getElementById('secao-adm').style.display = 'none';

                carregaChamados();
            }
            else if (user.role == 'ADMINISTRADOR')
                   {
                    document.getElementById('secao-login').style.display = 'none';
                    document.getElementById('secao-cadastro').style.display = 'none';
                    document.getElementById('secao-cliente').style.display = 'none';
                    document.getElementById('secao-adm').style.display = 'block';

                    carregaTodosChamados();
                   }
            alert(`Logado como: ${user.nome}`);
            document.getElementById('formulario-login').reset();
            })
    .catch(error => {alert(error.message);})
});


// CADASTRO
document.getElementById('formulario-cadastro').addEventListener('submit', (evento) => {
    evento.preventDefault();

    const nomet = document.getElementById('nome-cadastro').value.trim();
    const emailt = document.getElementById('email-cadastro').value.trim();
    const senhat = document.getElementById('senha-cadastro').value.trim();

    if (nomet == '' || senhat == '' || senhat.length < 6 || emailt == '') {
        alert('Preencha todos os dados corretamente.');
        return;
    }

    fetch ('http://localhost:8080/usuarios/post', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            role: 'CLIENTE',
            nome: nomet,
            email: emailt,
            senha: senhat
        })
    })
    .then(resposta => {
        if (resposta.status !== 201) 
        {
            throw new Error('Falha ao Criar Usuário: ' + resposta.status)
        }
        else {return resposta.json();}
    })
    .then(usuario => {
        alert(`Usuário ${usuario.email} cadastrado com sucesso!`);
        document.getElementById('secao-cadastro').style.display = 'none';
        document.getElementById('secao-login').style.display = 'block';
        document.getElementById('formulario-cadastro').reset();
    })
    .catch(error => {alert(error.message);})
});


//CLIENTE
// CARREGA LISTA DE CHAMADOS DO CLIENTE
function carregaChamados()
{
    const lista = document.getElementById('lista-chamados'); 
    lista.innerHTML = '';

    fetch('http://localhost:8080/chamados/meuschamados', {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('Auth')
        }
    })
    .then(resposta => { 
        if (!resposta.ok)
        {
            throw new Error('Chamados não encontrados.')
        }
        else {return resposta.json();}
    })
    .then(chamados => {
        chamados.forEach(chamado => {
            const linha = document.createElement('li');
            linha.textContent = `${chamado.id} - ${chamado.titulo} - Resposta: ${chamado.resposta}`;
            lista.appendChild(linha);
        })
    })
    .catch(erro => alert(erro.message))
}
// CRIA CHAMADO
document.getElementById('formulario-novo-chamado').addEventListener('submit', (evento) => {
    evento.preventDefault();

    const titulot = document.getElementById('titulo-chamado').value.trim();
    const descricaot = document.getElementById('descricao-chamado').value.trim();

    if (titulot == '' || descricaot == '') {
        alert('Preencha todos os dados corretamente.');
        return;
    }

    fetch ('http://localhost:8080/chamados', {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('Auth')
         },
        body: JSON.stringify({
            titulo: titulot,
            descricao: descricaot
        })
    })
    .then(resposta => {
        if (resposta.status !== 201) 
        {
            throw new Error('Falha ao Criar Chamado: ' + resposta.status)
        }
        else {return resposta.json();}
    })
    .then(usuario => {
        alert(`Chamado cadastrado com sucesso!`);
        document.getElementById('formulario-novo-chamado').reset();
        carregaChamados();
    })
    .catch(error => {alert(error.message);})
});
// LOGOUT CLIENTE
document.getElementById('botao-logout1').addEventListener('click', () => {

    const lista = document.getElementById('lista-chamados'); 
    lista.innerHTML = '';
    
    document.getElementById('secao-cliente').style.display = 'none';
    document.getElementById('secao-login').style.display = 'block';

    document.getElementById('formulario-login').reset();
});

//ADMINISTRADOR
// CARREGA LISTA DE TODOS OS CHAMADOS
function carregaTodosChamados()
{
    const tchamados = document.getElementById('todos-chamados'); 
    tchamados.innerHTML = '';

    fetch('http://localhost:8080/chamados/todoschamados', {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('Auth')
        }
    })
    .then(resposta => { 
        if (!resposta.ok)
        {
            throw new Error('Chamados não encontrados.')
        }
        else {return resposta.json();}
    })
    .then(chamados => {
        chamados.forEach(chamado => {
            const li = document.createElement('li');
            li.innerHTML = `
              <strong>${chamado.titulo}</strong>
              <p>${chamado.descricao || "Sem descrição"}</p>
              <p>${chamado.status}</p>
              <button onclick="mostrarFormulario(${chamado.id}, '${chamado.titulo}')">Responder</button>`;
            tchamados.appendChild(li);
        })
    })
    .catch(erro => alert(erro.message))
}
//MOSTRA FORMULARIO
function mostrarFormulario(idChamado, nomeChamado) {
    document.getElementById('titulo').style.display = 'block';
    document.getElementById('resposta-chamado').style.display = 'block';
    document.getElementById('chamado-id').value = idChamado;
    document.getElementById('nome-chamado').value = nomeChamado;
    localStorage.setItem('idChamado', idChamado); 
}
// RESPONDE CHAMADO
document.getElementById('resposta-chamado').addEventListener('submit', (evento) => {
    evento.preventDefault();
    
    const newresposta = document.getElementById('resposta').value.trim();

    if (newresposta == '') {
        alert('Resposta vazia.');
        return;
    }

    fetch(`http://localhost:8080/chamados/${localStorage.getItem('idChamado')}/responde`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('Auth')
        },
        body: JSON.stringify({
            resposta: newresposta,
            status: 'RESPONDIDO'
        })
    })
    .then(resposta => {
        if (resposta.ok) {
            alert('Resposta enviada com sucesso!');
            document.getElementById('resposta-chamado').reset();
            document.getElementById('titulo').style.display = 'none';
            document.getElementById('resposta-chamado').style.display = 'none';
            carregaTodosChamados();
            }
        else { throw new Error('Falha ao responder: ' + resposta.status)}
    })
    .catch(erro => alert(erro.message));
})
//LOGOUT ADM 
const botao = document.getElementById('botao-logout2');
botao.addEventListener('click', () => {
    document.getElementById('todos-chamados').innerHTML = '';

    document.getElementById('secao-adm').style.display = 'none';
    document.getElementById('secao-login').style.display = 'block';

    document.getElementById('resposta-chamado').reset();
});