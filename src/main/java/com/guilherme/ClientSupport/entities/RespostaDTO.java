package com.guilherme.ClientSupport.entities;

public class RespostaDTO {

    private String resposta;

    private String status;

    public RespostaDTO (){}

    public RespostaDTO (String resposta, String status)
    {
        this.resposta = resposta;
        this.status = status;
    }
    
    public String getResposta(){return this.resposta;}
    public String getStatus(){return this.status;}

    public void setResposta(String resposta){ this.resposta = resposta;}
    public void setStatus(String status){ this.status = status;}
}
