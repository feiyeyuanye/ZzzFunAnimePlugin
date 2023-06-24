package com.example.zzzfunanimeplugin.plugin.components

import com.su.mediabox.pluginapi.Constant

object Const {

    val host: String = "http://www.zzzfun.com"

    val ua = Constant.Request.USER_AGENT_ARRAY[0]

    const val INVALID_GREY: Int = 0xff999999.toInt()

    object Icon {
        const val RANK =
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJAAAACQCAMAAADQmBKKAAAAAXNSR0IArs4c6QAAAF1QTFRFR3BM/4On/4Kn/4Km/4Km/4Gl/4Gm/4Gm/6C4/4Km/4ir/4Sp/4Gm/4Kl/4Om/4Km/4Kn/4Km/4mn/4Om/4Gl//f5/8fX/4mr/+Pr//D0/7bL/5Kx/6rC/9bi/5+64z0lTwAAABR0Uk5TADtRgLrJ6/sG9RMlktpfrzSeGkg3NlI4AAAEu0lEQVR42tWcaZujIAyA6wX1qnUmRfH6/z9zZ2Z7TLcqIUHL5rOl7xMgCSHkcGBIFpyiukpKIVIpUyHKpKqjU5Ad3iBZGMUCFkTEUbgn1TEsSjBKWYTHXVRziiUgRcanbGvd4GluTBvqKS8EEEQU+SY4QQUSaCKrwD1ODCyJ3SKdmTg/SGdnOB81OJH6w83OilJwJGnkYMedE3AoyZmtHglORfKUlCfgXBKGVTqlsIGkJ+p0FbCRFKRpyxLYTBKCz81L2FBK64UUCNhUhKUr+UxhY0k/bXhCCZuLDP3isSH63IXniwg5a0EKO0mKWtm5gN1EIHZ/VsKOUhot5DGBXSUxeZECdpbC4N9hd1n1/Xm6P1Ca+7OAjMsogrdItBjPy/cAybNPE7YyaRG8TWYn7SN9H1A6d6at4Y1Sz6xoeKu8ruv4vUDxSxBk/k3X6gtJdNuZRw9sFdS0F4a0jaWKzApi8XwRWaqoMs7XhSnGWauevPzWCsKoKLcKyzQXSNuEakdzXH9hiznif3i0EHwAgtDGKO4BdN/5mfQDSGYWkf0eQPd4P/YF6DpnR+kASHUOgOQRu8eMQCOA4gNd91nhAKg32078MbbkA6nvTwY+UPmz6YENpBuEv0P9TYZdQoDx5BMfKESfflCOfGQDRehgeu2vhttHPRvo2xIJLtCvSEcxgaTAruk1oOnxVc/V0NeqDrhA4+/PFBcowObMFudL9U/fqZYHdMKmGOasz9gNc6ebflCjpgJF2CP9vzBq6Fe/7wdNAqrNB6BZoMn8i4kEVB0SElBj/kVDAkoOJQlIIVIBJKASaRfB+iA60Ra1oAI9/MXCotZUoJS67SfLXAkyuXeQVCC9tu9Hqh2SdKC1nI+60IHIU/aPFzPmbrBTJuhAS5t/YPgywQK69Ba5JCxQyQEC50Al0XWsZsVGBlBCdK6rq7pjAFXE8GN1UXOAakaAtug+OLssYoWwC96jZ4WwnCD/YaqbTjWLkZBdkM85Bj1wvn37I9xnHYMYB8Vr6rlRt1DjhqTJQIJ3lP5eQ/2TI/1Bmugn15iXbNDd8OLX1dBpXrLBQTrGVdLzbzom8wkoc5TScwVUOkt6OgIqnKWFHQGF7hLnToCuiXPG1UI3E+c3HfdqAeVfW4t71I52xfm4fMFcT6l157qaZsBmA+7XU5g5m7hAE37GcPus401Zh95jyEvgrzhQ0xe1HjCe/mhZvdiP1B0/9niriC0kWFQS4sZ+wI2eW5VaXKeDoKSxwY1d2Vbr0JSEVc9LvU6M/V2jbHhUgx03ti9oupskdGFKO+FHDTglX0glKYshY15RXI9QUtvbjHjmlg0ar+k7q+FqfmHlupkcrdQzX1hpXXraLVoAbacekAv1wrbFuUtKslTPckW1ffnynJnEm8K7gs7uCrxfzSTeFD4dV91VVA9PFkBP9iOsvaSgPBL4rSSCelYfCRCfUdx8STtRfn3a4qFJ12pUBa4hLPsvnuJ491jJv+dc/j148+9JoH+PJv17Vurfw1v/nib793jbv+ft/jUA8K9FgodNJDZoswHsXiSeNSLxsFWLf81sPGz342FDpGvLKLJs0DLKw6ZaHrYd87Axm4+t63xs7rdp+8M/3bQGIdOH2/wAAAAASUVORK5CYII="
        const val TABLE =
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJwAAACcCAMAAAC9ZjJ/AAAAYFBMVEUAAAA1vutF1/8tvegtveguv+otveguvectveg3yPMuveguv+otveguvegtveguvegtvegwwOotvegtveguvektvegtveguvugtvegtvOgvvuguveouvugvvukwwektvOdLgIVGAAAAH3RSTlMAFgTXtV/ri3cLQjr1n7yn8CvH0XCD4WutmWJTVjUgpUJm4gAABDJJREFUeNrM19luqzAUheFlGxwcA2EIhAztev+3PKp0qqpqEjww+LtH+rHwxkY8YfOhmuquHzWpx76rp2rIrcC+pC2qTvMF3VWFldiD/CjqkrPKuvjYOFBcsobOmuwisBF5z+gtu0usz14bBmmuFquSN8UI6iaxFnMZGWm8GKzBFJoL0MXyefInLT5PYlFHxQWpI5YjTlzYSWAZ8txwcc1ZYgFtz1X0LaLlJVdS5ohjKq6oMojQKq5KtQh2K7my8oZAAzcwIMiVm7jCn5y4kUnCk6m5mdrAi+i5oV7Ag+m5qd7Amay5sVqmtxd+TDKtGfLbNaHZGziNb9zJDbPakjspW8wwirtRBu9V3FGFt3LuKk/zg5v/7GTPnfUSr5y5uzNeEA131wg8d2ICTnjqyCQc8YRUTIKS+KtgIgr8YTQToU30wo11dXBS1SP9FHELp4cWHtpBRy3dhe7KwcCTGUq6u+AXOdKZtghgNZ2NMvT4qwSCCBV6KFZ01bQI1DZ0pYAfls4eCPagMxt0GewQoQu5KMrG45UiWLpqJL7d6WpElJGu7viW0dUBUQ50leE/Qb8X2mRLCP+/g0UU6/+XyOjsE1E+6Szz3qsUiCLou18tU4zjB74UacYV+FKnGVcDgCxj4tpH/sbjMzyulAAsI+KunDEEx9H6Xh6E91A9B8cVAKqIuJqzVHBcBaALjXN7VgfHdQB0RNzAWafgOA0IRsTJjDM6ERxHARsTB3mZsjemwiA8ziIn0xzCZI4h3bgBVbpxFaZ04ybU6cbV6NKN69CnG9djjIozh04912fH2LgROiZOdHzjFhmnwZi4A98ScXH8R725rTAIA0F0DTHkokigNS3e/v8v+1DagljrZgxsz3txSrLrOplQs5SbSgZMXIMtq152CeCyYgXh9/sUWhBgKxl2/pt2aCtBm7Ca/DZhxpuw7NeX6Be/6JFJ9LApekwPcsUFqjBx9b1bc51PEldhH9WqWzYw8RRxBrQj7IFTP8iOaAtMJRN9gIwcW8BlimeIs6B5WH9fEFxcQG3X2y/zBrFdYcN6sJcVyatzDGv5Vv8kU9wk/3iJtERxmp4kieLS6ycSxb2fpOWJ0xkBhEgQkXdez6xXTxCeUav80EsiiJRzOyKwtgIf9uYOOUGrxhGAazhBK35EzRKAzYuoqR5IQK+BE9y9ytyqHWXT5ZadM8UX1uZnce1STB3+AGeK3jZ2LRBiZg3EJilioZJhjMBocN60Y6wO1mgcW8MKzv/blYNHOXdsBAAIAzHsLy3770vBAtCALngPy5417M2FHoTstYqe0uydzx4h7YWUnm/tbdkevu1VPnmBDLTgGWzYwiZBbEzFZmiSugX4VDv6yEajbG7Lhsps4g3H8WxW0AYZccoSR0BtPhWHZ3GyF8eOdSYaB7ZXNTZp8iM7/QfU/QaHPwE+dRmE077EpAAAAABJRU5ErkJggg=="
        const val TOPIC =
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJAAAACQCAIAAABoJHXvAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAFiUAABYlAUlSJPAAAASDSURBVHhe7ZpbktswDMD2RL3/CXqtLceuMw7ih2SRFKUQg59ubIk0fvvzOxZ//+g7FCMEw/c1NTxRg+E7djEkwYLhkwUxEmGC4RsFNAa9g+GjDGFX+gXDVxjOTvQIhs2H1h33YFh4Dh1xDIYlJ9MLl2DYbWLtsQ+GlabXGMtg2OSrNMMsGBb4Qm2wCYbRv1YDtINh4lRURTUYBk1f6qEXDCOmUAmlYBguPVQDjWAYK72wmeZgGCi9tY22YBglLbSBhmAYIq3yKU+D4fr0gY94FAwXp4+tpz4YrkwbrSSD9baSymC4LFWxhppguCZVtJjiYLggVbeMDBbGMsqC4ejUyAIKguHQ1NQ7Mlgw77gLhuNSBy/JYPG85DIYDkrdPOc8GI5InT0hg0X1hJNgeDnt4hEZLLBHZLDYfnAUDO/09QX+bqTnXSV+EC9YCXilxRLwiqcffATDC57WgtdrrQWvu/lOjGAt4KgSW8BRDr4TIFg7OPDadnCggzveg+E5BxXByYdqgWOt3dE1mDo4H+qCw03dsQuGh6w1Are8tABXmLoxXTABF4lG4BZTNzoFs8btrv1Fpm70CDYZ2M7IjS0YfjZ1MrCdnQsZrBlsZ+eCe7ApwY5GLmQwDbCjkQtLMPxg6pRgRzszmA7Y0c4MpgbWNNI72MRgUyMzmBrY1MgMpgY2NTKDqYFNzfzBv22dGGxqZgZTApuamcGUwKZm+gYTZwVrmpnBNMCOlmYwDbCjpRlMA+xoaQbTADta6h5MnAxsZ2wGawbbGZvBmsF2xvYIJk4D9rI3g7WBvezNYG1gL3s7BRMnABu5mMEawEYuuv8nnL1Dg1187PC/pvYODXbxsXMwcVCwhZsZ7CHYws3+wcThwPyeZrAnYH5P/wcT8IOzA4HJPV2IEUwcAszs7EKYYGJ8MLCzCxmsGEzr78IWTMDPXQwL5vR3I1gwMSCYsIsb8YKJ0cB4XdwIGUyMAwbr5cYumICH+hoBjNTLHYGDiX3BMB3dETuY2AuM0dcd78EEPBpBfzBAX98ZIZjoBu6N4DsfwQS8EEQHcGMEPxgnmGgK7griB0MFE43ALXH84CiYgNdCqQ7Oj+MRAwZbVQFnRvOIk2ACXg5oIzgtmieMHGz1ATghpiecBxNwRFirwLsxPecymICDInsLno/sORMFWz0EzwT3krtgAo4bwhf4e3zvmDTYuN5REEzAoamRBZQFE3B0qm4ZGSyMZRQHE3BBqmgxNcEEXJOqWENlMAGXpY1WksF6W0l9MAFXpo+t51EwARenD3zE02ACrk+rfEpDMAFDpIU20BZMwCjprW00BxMwUHphMxrBBIyVHqqBUjABw6VQCb1gAkZMX+qhGkzAoKmoinawFUz8tRpgE0zA6F+oDWbBBCzwVZphGWwFm3yDltgHE7DPxNrjEmwFu02mF47BBCw5jY74BlvBtkPrTo9gK9h8ODvRL9gKvsIQdqV3sBV8kbAGIEawF/hAQYxEsGAv8Mm6GJKowV7gIzoYm/DB9uDLKjoOQwU7BJ/+1qH5/f0HzUYtfadAYCoAAAAASUVORK5CYII="
        const val UPDATE =
            "data:image/png;base64,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"
    }

}