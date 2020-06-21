Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		* set schemas.nutrient =
		"""
		{
            id: '##number',
            name: '#string',
            unitName: '#string',
            amount: '#number'
		}
		"""

		* set schemas.portion =
		"""
		{
            id: '##number',
            baseUnitName: '#string',
            baseUnitAmount: '#number',
            isNutrientRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
            description: '##string',
            displayUnitName: '##string',
            displayUnitAmount: '##number'
		}
		"""

		* set schemas.food =
        		"""
        		{
                    id: '##number',
                    fdcId: '##number',
                    description: '#string',
                    brandOwner: '##string',
                    ingredients: '##string',
                    nutrients: '##[] schemas.nutrient',
                    portions: '##[] schemas.portion'
        		}
        		"""